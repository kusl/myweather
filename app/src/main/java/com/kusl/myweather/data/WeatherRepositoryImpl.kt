package com.kusl.myweather.data

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.core.GridMath
import com.kusl.myweather.core.GridPoint
import com.kusl.myweather.core.SystemTimeSource
import com.kusl.myweather.core.Telemetry
import com.kusl.myweather.core.TimeSource
import com.kusl.myweather.data.local.dao.ForecastCacheDao
import com.kusl.myweather.data.local.dao.PointMetadataDao
import com.kusl.myweather.data.local.entity.ForecastCacheEntity
import com.kusl.myweather.data.local.entity.PointMetadataEntity
import com.kusl.myweather.data.mapper.NwsMappers
import com.kusl.myweather.data.remote.NwsApi
import com.kusl.myweather.data.remote.WeatherJson
import com.kusl.myweather.data.remote.dto.PointPropertiesDto
import com.kusl.myweather.domain.AreaWeatherResult
import com.kusl.myweather.domain.WeatherRepository
import com.kusl.myweather.domain.model.AreaWeather
import com.kusl.myweather.domain.model.CurrentObservation
import com.kusl.myweather.domain.model.Forecast
import com.kusl.myweather.domain.model.ForecastPeriod
import com.kusl.myweather.domain.model.LocationSources
import com.kusl.myweather.domain.model.PointMetadata
import com.kusl.myweather.domain.model.WeatherAlert
import com.kusl.myweather.domain.model.WeatherTile
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.serialization.encodeToString
import retrofit2.Response

/**
 * The only type that knows the freshness policy:
 *  - long-TTL `coordinate -> grid` metadata (effectively immutable),
 *  - short-TTL grid forecasts honouring NWS `Cache-Control` (clamped),
 *  - conditional GETs (`If-None-Match` -> `304`) to refresh expiry cheaply, and
 *  - serving a stale-but-valid cached copy when NWS is unreachable (offline-first).
 *
 * Beyond the core forecast it also gathers the *supplementary* information for
 * the **primary** location — active alerts, the hourly forecast, the latest
 * observation, and the set of official source endpoints — so the dashboard can
 * surface as much as NWS offers. Those fetches are strictly best-effort: each is
 * wrapped so a failure leaves the headline forecast untouched. They run only for
 * the coordinate path, not the lightweight grid-recenter peek.
 *
 * Transport-level de-duplication, negative caching, and stale-on-error for
 * *every* call (including the supplementary ones) are handled one layer down by
 * `HttpCacheInterceptor`; this class keeps the higher-level, domain-aware Room
 * cache for metadata and forecasts.
 *
 * Ported from the .NET dashboard's `WeatherService`.
 */
class WeatherRepositoryImpl(
    private val api: NwsApi,
    private val pointDao: PointMetadataDao,
    private val forecastDao: ForecastCacheDao,
    private val time: TimeSource = SystemTimeSource,
) : WeatherRepository {

    override suspend fun getAreaWeather(point: GeoPoint, radius: Int): AreaWeatherResult {
        val coord = point.rounded()
        Telemetry.i("WeatherRepository", "area for ${coord.toApiString()} r=$radius")

        return when (val meta = resolveMetadata(coord)) {
            is MetaResult.NoCoverage -> AreaWeatherResult.NoCoverage
            is MetaResult.Unavailable ->
                AreaWeatherResult.Unavailable("Couldn't reach the weather service. Check your connection and try again.")
            is MetaResult.Ok -> buildArea(
                center = meta.metadata.grid,
                radius = radius,
                metadata = meta.metadata,
                query = coord,
                metaStale = meta.stale,
                fetchSupplementary = true,
                queryPoint = coord,
                extras = meta.extras,
            )
        }
    }

    override suspend fun getAreaWeatherForGrid(
        center: GridPoint,
        radius: Int,
        label: String?,
        timeZone: String?,
    ): AreaWeatherResult {
        Telemetry.i("WeatherRepository", "recenter on $center r=$radius")
        // We already know the grid, so no /points lookup is needed. Synthesize
        // metadata for this cell, reusing the office time-zone we resolved earlier.
        val metadata = PointMetadata(
            queryLatitude = 0.0,
            queryLongitude = 0.0,
            gridId = center.gridId,
            gridX = center.gridX,
            gridY = center.gridY,
            city = label,
            state = null,
            timeZone = timeZone,
        )
        // No metadata staleness applies here; freshness is governed entirely by
        // the per-cell forecast cache. This is an ephemeral peek at a neighbouring
        // cell, so we skip the supplementary (alerts/hourly/observation) fetches.
        return buildArea(
            center = center,
            radius = radius,
            metadata = metadata,
            query = metadata.query,
            metaStale = false,
            fetchSupplementary = false,
            queryPoint = null,
            extras = null,
        )
    }

    /**
     * Fetch the [center] cell's forecast plus its surrounding [radius] ring
     * (cache-aside, bounded concurrency) and assemble them into an [AreaWeather].
     * Shared by the coordinate path ([getAreaWeather]) and the grid-recenter path
     * ([getAreaWeatherForGrid]).
     *
     * When [fetchSupplementary] is true the primary location is enriched with
     * alerts (for [queryPoint]), the hourly forecast, the latest observation, and
     * the source endpoints (from [extras]); all best-effort.
     */
    private suspend fun buildArea(
        center: GridPoint,
        radius: Int,
        metadata: PointMetadata,
        query: GeoPoint,
        metaStale: Boolean,
        fetchSupplementary: Boolean,
        queryPoint: GeoPoint?,
        extras: PointPropertiesDto?,
    ): AreaWeatherResult {
        val primary = when (val p = fetchForecast(center)) {
            is ForecastResult.Ok -> p
            ForecastResult.Unavailable ->
                return AreaWeatherResult.Unavailable("The forecast didn't load. Check your connection and try again.")
        }

        // Assemble the surrounding ring, cache-aside, with bounded concurrency so
        // even a cold area can't stampede NWS.
        val neighborGrids = GridMath.surrounding(center, radius)
        val semaphore = Semaphore(NEIGHBOR_CONCURRENCY)
        val neighborTiles = coroutineScope {
            neighborGrids.map { grid ->
                async {
                    val r = semaphore.withPermit { fetchForecast(grid) }
                    (r as? ForecastResult.Ok)?.let {
                        WeatherTile(
                            grid = grid,
                            forecast = it.forecast,
                            isPrimary = false,
                            distanceMeters = approxMeters(center, grid),
                        )
                    }
                }
            }.awaitAll().filterNotNull()
        }.sortedBy { it.distanceMeters ?: Double.MAX_VALUE }

        val primaryTile = WeatherTile(
            grid = center,
            forecast = primary.forecast,
            isPrimary = true,
            distanceMeters = 0.0,
        )

        // The "as much information as we can get" extras, for the primary location only.
        val supplementary = if (fetchSupplementary) {
            gatherSupplementary(center, queryPoint, extras)
        } else {
            SupplementaryData()
        }

        // Bound DB growth without discarding our offline fallback: only drop
        // entries that expired more than a week ago.
        runCatching { forecastDao.deleteExpiredBefore(time.nowMs() - STALE_RETENTION_MS) }

        val fromCache = metaStale || primary.stale
        Telemetry.i(
            "WeatherRepository",
            "assembled $center tiles=${neighborTiles.size + 1} cache=$fromCache " +
                "alerts=${supplementary.alerts.size} hourly=${supplementary.hourly.size} " +
                "obs=${supplementary.observation != null}",
        )
        return AreaWeatherResult.Success(
            AreaWeather(
                query = query,
                metadata = metadata,
                primary = primary.forecast,
                tiles = listOf(primaryTile) + neighborTiles,
                fromCache = fromCache,
                alerts = supplementary.alerts,
                hourly = supplementary.hourly,
                observation = supplementary.observation,
                sources = supplementary.sources,
            ),
        )
    }

    // ── supplementary data (best-effort; never blocks the core forecast) ───────

    private class SupplementaryData(
        val alerts: List<WeatherAlert> = emptyList(),
        val hourly: List<ForecastPeriod> = emptyList(),
        val observation: CurrentObservation? = null,
        val sources: LocationSources? = null,
    )

    /**
     * Gather alerts, the hourly forecast, the latest observation, and the source
     * endpoints concurrently. Every call is wrapped in `runCatching`, so any
     * failure degrades to an empty/null field rather than failing the load. All
     * of these requests still ride the transport cache, so repeated loads of the
     * same place don't re-hit NWS.
     */
    private suspend fun gatherSupplementary(
        center: GridPoint,
        queryPoint: GeoPoint?,
        extras: PointPropertiesDto?,
    ): SupplementaryData = coroutineScope {
        val coords = "${center.gridX},${center.gridY}"

        val alertsDeferred = async {
            val pt = queryPoint ?: return@async emptyList<WeatherAlert>()
            runCatching {
                val resp = api.getActiveAlerts(pt.toApiString())
                if (resp.isSuccessful) NwsMappers.toAlerts(resp.body()) else emptyList()
            }.getOrDefault(emptyList())
        }

        val hourlyDeferred = async {
            runCatching {
                val resp = api.getForecastHourly(center.gridId, coords)
                if (resp.isSuccessful) {
                    NwsMappers.toForecast(center, resp.body())?.periods?.take(HOURLY_LIMIT).orEmpty()
                } else {
                    emptyList()
                }
            }.getOrDefault(emptyList())
        }

        val observationDeferred = async {
            runCatching {
                val stationsResp = api.getObservationStations(center.gridId, coords)
                val stationId =
                    if (stationsResp.isSuccessful) NwsMappers.firstStationId(stationsResp.body()) else null
                if (stationId == null) {
                    null
                } else {
                    val obsResp = api.getLatestObservation(stationId)
                    if (obsResp.isSuccessful) NwsMappers.toObservation(obsResp.body(), stationId) else null
                }
            }.getOrNull()
        }

        SupplementaryData(
            alerts = alertsDeferred.await(),
            hourly = hourlyDeferred.await(),
            observation = observationDeferred.await(),
            sources = extras?.let(::toSources),
        )
    }

    private fun toSources(p: PointPropertiesDto) = LocationSources(
        forecastOffice = p.forecastOffice,
        radarStation = p.radarStation,
        forecastZone = p.forecastZone,
        county = p.county,
        fireWeatherZone = p.fireWeatherZone,
        forecastUrl = p.forecast,
        hourlyForecastUrl = p.forecastHourly,
        gridDataUrl = p.forecastGridData,
        observationStationsUrl = p.observationStations,
    )

    // ── metadata (long-lived) ─────────────────────────────────────────────────

    private sealed interface MetaResult {
        data class Ok(
            val metadata: PointMetadata,
            val stale: Boolean,
            /** Raw point properties, present only on a fresh resolution (for source endpoints). */
            val extras: PointPropertiesDto? = null,
        ) : MetaResult

        data object NoCoverage : MetaResult
        data object Unavailable : MetaResult
    }

    private suspend fun resolveMetadata(coord: GeoPoint): MetaResult {
        val key = coord.toCacheKey()
        val now = time.nowMs()
        val cached = pointDao.getByKey(key)
        if (cached != null && now < cached.expiresAtEpochMs) {
            return MetaResult.Ok(cached.toDomain(), stale = false)
        }

        val response = runCatching { api.getPoint(coord.toApiString()) }.getOrNull()
            ?: return cached?.let { MetaResult.Ok(it.toDomain(), stale = true) } ?: MetaResult.Unavailable

        if (response.code() == 404) {
            // Coverage doesn't change; trust a stale row if we have one.
            return cached?.let { MetaResult.Ok(it.toDomain(), stale = true) } ?: MetaResult.NoCoverage
        }
        if (!response.isSuccessful) {
            return cached?.let { MetaResult.Ok(it.toDomain(), stale = true) } ?: MetaResult.Unavailable
        }

        val body = response.body()
        val mapped = NwsMappers.toPointMetadata(coord, body)
            ?: return cached?.let { MetaResult.Ok(it.toDomain(), stale = true) } ?: MetaResult.NoCoverage

        pointDao.upsert(mapped.toEntity(key, retrievedAt = now, expiresAt = now + METADATA_TTL_MS))
        return MetaResult.Ok(mapped, stale = false, extras = body?.properties)
    }

    // ── forecast (short-lived, conditional) ────────────────────────────────────

    private sealed interface ForecastResult {
        data class Ok(val forecast: Forecast, val stale: Boolean) : ForecastResult
        data object Unavailable : ForecastResult
    }

    private suspend fun fetchForecast(grid: GridPoint): ForecastResult {
        val key = grid.toString()
        val now = time.nowMs()
        val cached = forecastDao.getByKey(key)
        if (cached != null && now < cached.expiresAtEpochMs) {
            return ForecastResult.Ok(cached.decodeForecast(), stale = false)
        }

        val response = runCatching {
            api.getForecast(grid.gridId, "${grid.gridX},${grid.gridY}", ifNoneMatch = cached?.etag)
        }.getOrNull()
            ?: return cached?.let { ForecastResult.Ok(it.decodeForecast(), stale = true) } ?: ForecastResult.Unavailable

        return when {
            // Cheapest path: unchanged on the server — just extend the TTL.
            response.code() == 304 && cached != null -> {
                val renewed = cached.copy(
                    etag = response.etag() ?: cached.etag,
                    retrievedAtEpochMs = now,
                    expiresAtEpochMs = now + computeTtlMs(response),
                )
                forecastDao.upsert(renewed)
                ForecastResult.Ok(cached.decodeForecast(), stale = false)
            }

            response.isSuccessful -> {
                val forecast = NwsMappers.toForecast(grid, response.body())
                if (forecast == null) {
                    cached?.let { ForecastResult.Ok(it.decodeForecast(), stale = true) } ?: ForecastResult.Unavailable
                } else {
                    forecastDao.upsert(
                        ForecastCacheEntity(
                            gridKey = key,
                            gridId = grid.gridId,
                            gridX = grid.gridX,
                            gridY = grid.gridY,
                            payloadJson = WeatherJson.encodeToString(forecast),
                            etag = response.etag(),
                            retrievedAtEpochMs = now,
                            expiresAtEpochMs = now + computeTtlMs(response),
                        ),
                    )
                    ForecastResult.Ok(forecast, stale = false)
                }
            }

            // 404 (no such cell — common at office edges), 429, 5xx, etc.:
            // serve a stale copy if we have one, otherwise report unavailable.
            else -> {
                if (response.code() !in intArrayOf(404, 304)) {
                    Telemetry.w("WeatherRepository", "NWS forecast for $grid returned HTTP ${response.code()}")
                }
                cached?.let { ForecastResult.Ok(it.decodeForecast(), stale = true) } ?: ForecastResult.Unavailable
            }
        }
    }

    // ── TTL policy (mirrors the .NET ComputeExpiry) ─────────────────────────────

    private fun computeTtlMs(response: Response<*>): Long {
        val maxAge = parseMaxAgeSeconds(response.headers()["Cache-Control"])
        var ttl = maxAge?.let { it * 1000L } ?: DEFAULT_FORECAST_TTL_MS
        if (ttl <= 0L) ttl = DEFAULT_FORECAST_TTL_MS
        if (ttl > MAX_FORECAST_TTL_MS) ttl = MAX_FORECAST_TTL_MS
        return ttl
    }

    companion object {
        // Nominal NWS cell size (~2.5 km), used only to order/label neighbours.
        private const val NOMINAL_CELL_METERS = 2_500.0
        private const val NEIGHBOR_CONCURRENCY = 4

        /** Cap on hourly periods surfaced (the API returns ~156; the next day is plenty). */
        private const val HOURLY_LIMIT = 24

        val METADATA_TTL_MS = 30L * 24 * 60 * 60 * 1000        // 30 days
        val DEFAULT_FORECAST_TTL_MS = 30L * 60 * 1000          // 30 minutes
        val MAX_FORECAST_TTL_MS = 6L * 60 * 60 * 1000          // 6 hours
        private val STALE_RETENTION_MS = 7L * 24 * 60 * 60 * 1000 // keep stale rows a week

        private val MAX_AGE_REGEX = Regex("""max-age\s*=\s*(\d+)""", RegexOption.IGNORE_CASE)

        fun parseMaxAgeSeconds(cacheControl: String?): Long? =
            cacheControl?.let { MAX_AGE_REGEX.find(it)?.groupValues?.getOrNull(1)?.toLongOrNull() }

        fun approxMeters(origin: GridPoint, cell: GridPoint): Double {
            val dx = (cell.gridX - origin.gridX).toDouble()
            val dy = (cell.gridY - origin.gridY).toDouble()
            return Math.sqrt(dx * dx + dy * dy) * NOMINAL_CELL_METERS
        }
    }
}

// ── entity <-> domain helpers ───────────────────────────────────────────────────

private fun PointMetadataEntity.toDomain() = PointMetadata(
    queryLatitude = queryLatitude,
    queryLongitude = queryLongitude,
    gridId = gridId,
    gridX = gridX,
    gridY = gridY,
    city = city,
    state = state,
    timeZone = timeZone,
)

private fun PointMetadata.toEntity(key: String, retrievedAt: Long, expiresAt: Long) = PointMetadataEntity(
    cacheKey = key,
    queryLatitude = queryLatitude,
    queryLongitude = queryLongitude,
    gridId = gridId,
    gridX = gridX,
    gridY = gridY,
    city = city,
    state = state,
    timeZone = timeZone,
    retrievedAtEpochMs = retrievedAt,
    expiresAtEpochMs = expiresAt,
)

private fun ForecastCacheEntity.decodeForecast(): Forecast =
    WeatherJson.decodeFromString(Forecast.serializer(), payloadJson)

private fun Response<*>.etag(): String? = headers()["ETag"]
