package com.kusl.myweather.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GeoPointTest {

    @Test
    fun `rounds to four decimal places`() {
        val p = GeoPoint(37.087912, -76.450567).rounded()
        assertEquals(37.0879, p.latitude, 0.0)
        assertEquals(-76.4506, p.longitude, 0.0)
    }

    @Test
    fun `api string is invariant and four dp`() {
        // Must use a dot decimal separator regardless of default locale.
        assertEquals("37.0879,-76.4506", GeoPoint(37.0879, -76.4506).toApiString())
        assertEquals("9.5000,-1.2500", GeoPoint(9.5, -1.25).toApiString())
    }

    @Test
    fun `cache key matches rounded api string`() {
        assertEquals("37.0879,-76.4506", GeoPoint(37.08792, -76.45061).toCacheKey())
    }

    @Test
    fun `parse accepts comma and space separated`() {
        assertEquals(GeoPoint(40.0, -75.0), GeoPoint.parse("40, -75"))
        assertEquals(GeoPoint(40.0, -75.0), GeoPoint.parse("40 -75"))
        assertEquals(GeoPoint(40.5, -75.25), GeoPoint.parse("  40.5 , -75.25 "))
    }

    @Test
    fun `parse rejects nonsense and out of range`() {
        assertNull(GeoPoint.parse("hello"))
        assertNull(GeoPoint.parse("40"))
        assertNull(GeoPoint.parse("91, 0"))
        assertNull(GeoPoint.parse("0, 200"))
    }

    @Test
    fun `isValid enforces bounds`() {
        assertTrue(GeoPoint.isValid(-90.0, 180.0))
        assertTrue(GeoPoint.isValid(90.0, -180.0))
        assertFalse(GeoPoint.isValid(90.001, 0.0))
        assertFalse(GeoPoint.isValid(0.0, 180.001))
        assertFalse(GeoPoint.isValid(Double.NaN, 0.0))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `constructor rejects bad latitude`() {
        GeoPoint(120.0, 0.0)
    }

    @Test
    fun `haversine distance is roughly correct`() {
        // ~1 degree of latitude is ~111 km.
        val d = GeoPoint(40.0, -75.0).distanceMetersTo(GeoPoint(41.0, -75.0))
        assertTrue("expected ~111km but was $d", d in 110_000.0..112_500.0)
        // Same point => zero.
        assertEquals(0.0, GeoPoint(40.0, -75.0).distanceMetersTo(GeoPoint(40.0, -75.0)), 1e-6)
    }
}
