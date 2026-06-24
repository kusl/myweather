package com.kusl.myweather.data

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.core.GridPoint
import com.kusl.myweather.core.TimeSource
import com.kusl.myweather.data.local.dao.ForecastCacheDao
import com.kusl.myweather.data.local.dao.PointMetadataDao
import com.kusl.myweather.data.local.entity.ForecastCacheEntity
import com.kusl.myweather.data.local.entity.PointMetadataEntity
import com.kusl.myweather.data.remote.NwsApi
import com.kusl.myweather.data.remote.WeatherJson
import com.kusl.myweather.data.remote.dto.AlertFeatureDto
import com.kusl.myweather.data.remote.dto.AlertPropertiesDto
import com.kusl.myweather.data.remote.dto.AlertsResponseDto
import com.kusl.myweather.data.remote.dto.ForecastPropertiesDto
import com.kusl.myweather.data.remote.dto.ForecastResponseDto
import com.kusl.myweather.data.remote.dto.ObservationResponseDto
import com.kusl.myweather.data.remote.dto.PeriodDto
import com.kusl.myweather.data.remote.dto.PointPropertiesDto
import com.kusl.myweather.data.remote.dto.PointResponseDto
import com.kusl.myweather.data.remote.dto.QuantitativeValueDto
import com.kusl.myweather.data.remote.dto.RelativeLocationDto
import com.kusl.myweather.data.remote.dto.RelativeLocationPropertiesDto
import com.kusl.myweather.data.remote.dto.StationsResponseDto
import com.kusl.myweather.domain.AreaWeatherResult
import com.kusl.myweather.domain.model.Forecast
import com.kusl.myweather.domain.model.ForecastPeriod
import kotlinx.coroutines.test.runTest
import okhttp3.Headers
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class WeatherRepositoryTest {

    private val query = GeoPoint(36.85, -76.28)
    private val primaryGrid = GridPoint("AKQ", 83, 61)

    private lateinit var api: FakeNwsApi
    private lateinit var pointDao: FakePointMetadataDao
    private lateinit var forecastDao: FakeForecastCacheDao
    private lateinit var time: MutableTimeSource
    private lateinit var repo: WeatherRepositoryImpl

    @Before
    fun setUp() {
        api = FakeNwsApi()
        pointDao = FakePointMetadataDao()
        forecastDao = FakeForecastCacheDao()
        time = MutableTimeSource(1_000_000_000L)
        repo = WeatherRepositoryImpl(api, pointDao, forecastDao, time)
    }

    @Test
    fun `cold load fetches, caches, and assembles a 3x3 tile matrix`() = runTest {
        val result = repo.getAreaWeather(query, radius = 1)

        assertTrue(result is AreaWeatherResult.Success)
        val area = (result as AreaWeatherResult.Success).area
        assertEquals("Norfolk, VA", area.metadata.displayName)
        assertEquals(primaryGrid, area.primary.grid)
        assertFalse(area.fromCache)
        // primary + 8 neighbours (none clipped at x=83,y=61)
        assertEquals(9, area.tiles.size)
        assertTrue(area.tiles.first().isPrimary)
        assertEquals(1, area.tiles.count { it.isPrimary })

        // Persisted for next time.
        assertNotNull(pointDao.getByKey(query.toCacheKey()))
        assertEquals(9, forecastDao.store.size)
    }

    @Test
    fun `fresh cache is served with no network calls`() = runTest {
        primeFreshMetadata()
        // Prime fresh forecasts for primary + all neighbours.
        (listOf(primaryGrid) + com.kusl.myweather.core.GridMath.surrounding(primaryGrid, 1)).forEach {
            primeForecast(it, expiresInMs = 60_000, etag = "v1", temp = 70)
        }
        api.throwOnPoint = true
        api.throwOnForecast = true

        val result = repo.getAreaWeather(query, radius = 1)

        assertTrue(result is AreaWeatherResult.Success)
        assertFalse((result as AreaWeatherResult.Success).area.fromCache)
        // The forecast/grid path is fully served from cache. (Supplementary
        // endpoints — alerts/hourly/observation — are best-effort extras that
        // never gate this result, so they aren't asserted here.)
        assertEquals(0, api.pointCallCount)
        assertTrue(api.forecastCalls.isEmpty())
    }

    @Test
    fun `stale cache is served when the network is down`() = runTest {
        primeFreshMetadata(expiresInMs = -1) // expired metadata
        (listOf(primaryGrid) + com.kusl.myweather.core.GridMath.surrounding(primaryGrid, 1)).forEach {
            primeForecast(it, expiresInMs = -1, etag = "v1", temp = 55) // expired
        }
        api.throwOnPoint = true
        api.throwOnForecast = true

        val result = repo.getAreaWeather(query, radius = 1)

        assertTrue(result is AreaWeatherResult.Success)
        val area = (result as AreaWeatherResult.Success).area
        assertTrue(area.fromCache)
        assertEquals(55, area.primary.current!!.temperature)
    }

    @Test
    fun `network down with empty cache reports unavailable`() = runTest {
        api.throwOnPoint = true
        val result = repo.getAreaWeather(query, radius = 1)
        assertTrue(result is AreaWeatherResult.Unavailable)
    }

    @Test
    fun `point 404 with no cache reports no coverage`() = runTest {
        api.pointResponder = { Response.error(404, "".toResponseBody(null)) }
        val result = repo.getAreaWeather(query, radius = 1)
        assertEquals(AreaWeatherResult.NoCoverage, result)
    }

    @Test
    fun `expired forecast triggers a conditional request with the stored ETag`() = runTest {
        primeFreshMetadata() // so no /points call is needed
        primeForecast(primaryGrid, expiresInMs = -1, etag = "v1", temp = 60) // expired, has ETag

        repo.getAreaWeather(query, radius = 1)

        val primaryCall = api.forecastCalls.firstOrNull { it.second == "83,61" }
        assertNotNull(primaryCall)
        assertEquals("v1", primaryCall!!.third) // If-None-Match echoed the cached ETag
        // And the refreshed entry adopts the new ETag from the response.
        assertEquals("v2", forecastDao.getByKey(primaryGrid.toString())!!.etag)
    }

    @Test
    fun `forecast ttl is clamped to the maximum`() = runTest {
        api.forecastResponder = { _, _, _ ->
            Response.success(
                forecastDto(72),
                Headers.headersOf("Cache-Control", "public, max-age=99999999", "ETag", "v2"),
            )
        }
        repo.getAreaWeather(query, radius = 1)
        val entry = forecastDao.getByKey(primaryGrid.toString())!!
        assertEquals(WeatherRepositoryImpl.MAX_FORECAST_TTL_MS, entry.expiresAtEpochMs - time.nowMs())
    }

    @Test
    fun `forecast ttl falls back to default without cache-control`() = runTest {
        api.forecastResponder = { _, _, _ ->
            Response.success(forecastDto(72), Headers.headersOf("ETag", "v2"))
        }
        repo.getAreaWeather(query, radius = 1)
        val entry = forecastDao.getByKey(primaryGrid.toString())!!
        assertEquals(WeatherRepositoryImpl.DEFAULT_FORECAST_TTL_MS, entry.expiresAtEpochMs - time.nowMs())
    }

    @Test
    fun `recenter on a grid assembles the matrix without a points lookup`() = runTest {
        val center = GridPoint("AKQ", 84, 61)

        val result = repo.getAreaWeatherForGrid(
            center = center,
            radius = 1,
            label = "Selected grid cell",
            timeZone = "America/New_York",
        )

        assertTrue(result is AreaWeatherResult.Success)
        val area = (result as AreaWeatherResult.Success).area
        assertEquals(center, area.primary.grid)
        assertEquals(center, area.tiles.first { it.isPrimary }.grid)
        assertEquals(9, area.tiles.size)             // centre + 8 neighbours
        assertEquals(0, api.pointCallCount)          // grid path never hits /points
        assertEquals("Selected grid cell", area.metadata.displayName)
        assertEquals("America/New_York", area.metadata.timeZone)
        assertFalse(area.fromCache)
    }

    @Test
    fun `active alerts are fetched and propagated for the primary location`() = runTest {
        api.alertsResponder = {
            Response.success(
                AlertsResponseDto(
                    features = listOf(
                        AlertFeatureDto(
                            id = "urn:oid:tornado-1",
                            properties = AlertPropertiesDto(
                                id = "urn:oid:tornado-1",
                                event = "Tornado Warning",
                                severity = "Extreme",
                                urgency = "Immediate",
                                certainty = "Observed",
                                headline = "Tornado Warning issued for Norfolk",
                                description = "A tornado was spotted near Norfolk.",
                                instruction = "Take shelter now.",
                                areaDesc = "Norfolk, VA",
                                expires = "2026-06-19T13:00:00-04:00",
                                senderName = "NWS Wakefield VA",
                            ),
                        ),
                    ),
                ),
            )
        }

        val result = repo.getAreaWeather(query, radius = 1)

        assertTrue(result is AreaWeatherResult.Success)
        val area = (result as AreaWeatherResult.Success).area
        assertEquals(1, area.alerts.size)
        val alert = area.alerts.first()
        assertEquals("Tornado Warning", alert.event)
        assertEquals("A tornado was spotted near Norfolk.", alert.description) // carried verbatim
        assertEquals("Take shelter now.", alert.instruction)
        assertEquals(1, api.alertsCallCount)
    }

    @Test
    fun `grid recenter does not fetch supplementary alerts`() = runTest {
        val center = GridPoint("AKQ", 84, 61)

        repo.getAreaWeatherForGrid(
            center = center,
            radius = 1,
            label = "Selected grid cell",
            timeZone = "America/New_York",
        )

        // Recenter is an ephemeral peek at a neighbouring cell, not the user's
        // own location, so it deliberately skips the supplementary fetches.
        assertEquals(0, api.alertsCallCount)
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private fun primeFreshMetadata(expiresInMs: Long = 30L * 24 * 60 * 60 * 1000) {
        pointDao.store[query.toCacheKey()] = PointMetadataEntity(
            cacheKey = query.toCacheKey(),
            queryLatitude = query.latitude,
            queryLongitude = query.longitude,
            gridId = primaryGrid.gridId,
            gridX = primaryGrid.gridX,
            gridY = primaryGrid.gridY,
            city = "Norfolk",
            state = "VA",
            timeZone = "America/New_York",
            retrievedAtEpochMs = time.nowMs(),
            expiresAtEpochMs = time.nowMs() + expiresInMs,
        )
    }

    private fun primeForecast(grid: GridPoint, expiresInMs: Long, etag: String?, temp: Int) {
        val forecast = Forecast(
            gridId = grid.gridId, gridX = grid.gridX, gridY = grid.gridY,
            generatedAt = null, updateTime = null,
            periods = listOf(
                ForecastPeriod(
                    number = 1, name = "Now", startTime = "", endTime = "", isDaytime = true,
                    temperature = temp, temperatureUnit = "F", probabilityOfPrecipitation = 10,
                    windSpeed = "5 mph", windDirection = "N", shortForecast = "Clear",
                    detailedForecast = "Clear.", icon = "",
                ),
            ),
        )
        forecastDao.store[grid.toString()] = ForecastCacheEntity(
            gridKey = grid.toString(), gridId = grid.gridId, gridX = grid.gridX, gridY = grid.gridY,
            payloadJson = WeatherJson.encodeToString(Forecast.serializer(), forecast),
            etag = etag,
            retrievedAtEpochMs = time.nowMs(),
            expiresAtEpochMs = time.nowMs() + expiresInMs,
        )
    }
}

// ── fakes ───────────────────────────────────────────────────────────────────────

private fun pointDto(): PointResponseDto = PointResponseDto(
    PointPropertiesDto(
        gridId = "AKQ", gridX = 83, gridY = 61, timeZone = "America/New_York",
        relativeLocation = RelativeLocationDto(RelativeLocationPropertiesDto("Norfolk", "VA")),
    ),
)

private fun forecastDto(temp: Int): ForecastResponseDto = ForecastResponseDto(
    ForecastPropertiesDto(
        generatedAt = "2026-06-19T12:00:00Z", updateTime = "2026-06-19T11:30:00Z",
        periods = listOf(
            PeriodDto(
                number = 1, name = "This Afternoon", startTime = "", endTime = "", isDaytime = true,
                temperature = temp, temperatureUnit = "F",
                probabilityOfPrecipitation = QuantitativeValueDto(value = 10.0),
                windSpeed = "5 mph", windDirection = "N", icon = "",
                shortForecast = "Clear", detailedForecast = "Clear.",
            ),
        ),
    ),
)

private class FakeNwsApi : NwsApi {
    var throwOnPoint = false
    var throwOnForecast = false
    var pointCallCount = 0
    val forecastCalls = mutableListOf<Triple<String, String, String?>>()

    // Supplementary endpoints are tracked separately so the core points/forecast
    // assertions above stay unaffected by the "as much info as we can get" extras.
    var alertsCallCount = 0

    var pointResponder: () -> Response<PointResponseDto> = { Response.success(pointDto()) }
    // Default success carries a Cache-Control + a fresh ETag ("v2").
    var forecastResponder: (String, String, String?) -> Response<ForecastResponseDto> = { _, _, _ ->
        Response.success(
            forecastDto(75),
            Headers.headersOf("Cache-Control", "max-age=1800", "ETag", "v2"),
        )
    }

    // No active alerts by default; tests override to inject one.
    var alertsResponder: (String) -> Response<AlertsResponseDto> = {
        Response.success(AlertsResponseDto(features = emptyList()))
    }

    override suspend fun getPoint(point: String): Response<PointResponseDto> {
        pointCallCount++
        if (throwOnPoint) throw IOException("offline")
        return pointResponder()
    }

    override suspend fun getForecast(
        office: String,
        coords: String,
        ifNoneMatch: String?,
    ): Response<ForecastResponseDto> {
        forecastCalls += Triple(office, coords, ifNoneMatch)
        if (throwOnForecast) throw IOException("offline")
        return forecastResponder(office, coords, ifNoneMatch)
    }

    override suspend fun getActiveAlerts(point: String): Response<AlertsResponseDto> {
        alertsCallCount++
        return alertsResponder(point)
    }

    // The remaining supplementary endpoints are inert by default (empty payloads),
    // so the repository's best-effort extras resolve to "nothing to show" without
    // touching the counters the core tests assert on.
    override suspend fun getForecastHourly(
        office: String,
        coords: String,
        ifNoneMatch: String?,
    ): Response<ForecastResponseDto> = Response.success(ForecastResponseDto(properties = null))

    override suspend fun getObservationStations(
        office: String,
        coords: String,
    ): Response<StationsResponseDto> = Response.success(StationsResponseDto(features = emptyList()))

    override suspend fun getLatestObservation(
        stationId: String,
    ): Response<ObservationResponseDto> = Response.success(ObservationResponseDto(properties = null))
}

private class FakePointMetadataDao : PointMetadataDao {
    val store = mutableMapOf<String, PointMetadataEntity>()
    override suspend fun getByKey(key: String) = store[key]
    override suspend fun upsert(entity: PointMetadataEntity) { store[entity.cacheKey] = entity }
}

private class FakeForecastCacheDao : ForecastCacheDao {
    val store = mutableMapOf<String, ForecastCacheEntity>()
    override suspend fun getByKey(key: String) = store[key]
    override suspend fun upsert(entity: ForecastCacheEntity) { store[entity.gridKey] = entity }
    override suspend fun deleteExpiredBefore(cutoffEpochMs: Long) {
        store.entries.removeAll { it.value.expiresAtEpochMs < cutoffEpochMs }
    }
}

private class MutableTimeSource(private var now: Long) : TimeSource {
    override fun nowMs(): Long = now
    fun advanceBy(ms: Long) { now += ms }
}
