package com.kusl.myweather.data

import com.kusl.myweather.core.GridPoint
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** Unit tests for the pure pieces of the cache-freshness policy. */
class CacheFreshnessTest {

    @Test
    fun `parses max-age from Cache-Control`() {
        assertEquals(2592000L, WeatherRepositoryImpl.parseMaxAgeSeconds("public, max-age=2592000"))
        assertEquals(1800L, WeatherRepositoryImpl.parseMaxAgeSeconds("max-age=1800"))
        assertEquals(0L, WeatherRepositoryImpl.parseMaxAgeSeconds("no-cache, max-age=0"))
    }

    @Test
    fun `max-age parsing is case insensitive and tolerant of spaces`() {
        assertEquals(60L, WeatherRepositoryImpl.parseMaxAgeSeconds("Max-Age = 60"))
    }

    @Test
    fun `missing or malformed Cache-Control yields null`() {
        assertNull(WeatherRepositoryImpl.parseMaxAgeSeconds(null))
        assertNull(WeatherRepositoryImpl.parseMaxAgeSeconds("no-store"))
        assertNull(WeatherRepositoryImpl.parseMaxAgeSeconds(""))
    }

    @Test
    fun `approx neighbour distance is zero at origin and grows with separation`() {
        val origin = GridPoint("AKQ", 50, 60)
        assertEquals(0.0, WeatherRepositoryImpl.approxMeters(origin, origin), 0.0)
        val near = WeatherRepositoryImpl.approxMeters(origin, GridPoint("AKQ", 51, 60))
        val far = WeatherRepositoryImpl.approxMeters(origin, GridPoint("AKQ", 53, 64))
        assertTrue(near > 0.0)
        assertTrue(far > near)
    }

    @Test
    fun `ttl constants encode the documented policy`() {
        assertEquals(30L * 24 * 60 * 60 * 1000, WeatherRepositoryImpl.METADATA_TTL_MS)
        assertEquals(30L * 60 * 1000, WeatherRepositoryImpl.DEFAULT_FORECAST_TTL_MS)
        assertEquals(6L * 60 * 60 * 1000, WeatherRepositoryImpl.MAX_FORECAST_TTL_MS)
    }
}
