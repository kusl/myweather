package com.kusl.myweather.data

import android.util.Log
import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.core.GridMath
import com.kusl.myweather.core.GridPoint
import com.kusl.myweather.core.SystemTimeSource
import com.kusl.myweather.core.TimeSource
import com.kusl.myweather.data.local.dao.ForecastCacheDao
import com.kusl.myweather.data.local.dao.PointMetadataDao
import com.kusl.myweather.data.local.entity.ForecastCacheEntity
import com.kusl.myweather.data.local.entity.PointMetadataEntity
import com.kusl.myweather.data.mapper.NwsMappers
import com.kusl.myweather.data.remote.NwsApi
import com.kusl.myweather.data.remote.WeatherJson
import com.kusl.myweather.domain.AreaWeatherResult
import com.kusl.myweather.domain.WeatherRepository
import com.kusl.myweather.domain.model.AreaWeather
import com.kusl.myweather.domain.model.Forecast
import com.kusl.myweather.domain.model.PointMetadata
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

        return when (val meta = resolveMetadata(coord)) {
            is MetaResult.NoCoverage -> AreaWeatherResult.NoCoverage
            is MetaResult.Unavailable ->
                AreaWeatherResult.Unavailable("Couldn't reach the weather service. Check your connection and try again.")
            is MetaResult.Ok -> {
                val primary = when (val p = fetchForecast(meta.metadata.grid)) {
                    is ForecastResult.Ok -> p
                    ForecastResult.Unavailable ->
                        return AreaWeatherResult.Unavailable("The forecast didn't load. Check your connection and try again.")
                }

                // Assemble the surrounding ring, cache-aside, with bounded
                // concurrency so even a cold area can't stampede NWS.
                val neighborGrids = GridMath.surrounding(meta.metadata.grid, radius)
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
                                    distanceMeters = approxMeters(meta.metadata.grid, grid),
                                )
                            }
                        }
                    }.awaitAll().filterNotNull()
                }.sortedBy { it.distanceMeters ?: Double.MAX_VALUE }

                val primaryTile = WeatherTile(
                    grid = meta.metadata.grid,
                    forecast = primary.forecast,
                    isPrimary = true,
                    distanceMeters = 0.0,
                )

                // Bound DB growth without discarding our offline fallback: only
                // drop entries that expired more than a week ago.
                runCatching { forecastDao.deleteExpiredBefore(time.nowMs() - STALE_RETENTION_MS) }

                val fromCache = meta.stale || primary.stale
                AreaWeatherResult.Success(
                    AreaWeather(
                        query = coord,
                        metadata = meta.metadata,
                        primary = primary.forecast,
                        tiles = listOf(primaryTile) + neighborTiles,
                        fromCache = fromCache,
                    ),
                )
            }
        }
    }

    // ── metadata (long-lived) ─────────────────────────────────────────────────

    private sealed interface MetaResult {
        data class Ok(val metadata: PointMetadata, val stale: Boolean) : MetaResult
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

        val mapped = NwsMappers.toPointMetadata(coord, response.body())
            ?: return cached?.let { MetaResult.Ok(it.toDomain(), stale = true) } ?: MetaResult.NoCoverage

        pointDao.upsert(mapped.toEntity(key, retrievedAt = now, expiresAt = now + METADATA_TTL_MS))
        return MetaResult.Ok(mapped, stale = false)
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
                    Log.w(TAG, "NWS forecast for $grid returned HTTP ${response.code()}")
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
        private const val TAG = "WeatherRepository"

        // Nominal NWS cell size (~2.5 km), used only to order/label neighbours.
        private const val NOMINAL_CELL_METERS = 2_500.0
        private const val NEIGHBOR_CONCURRENCY = 4

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
