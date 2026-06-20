package com.kusl.myweather.data.mapper

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.core.GridPoint
import com.kusl.myweather.data.remote.WeatherJson
import com.kusl.myweather.data.remote.dto.ForecastResponseDto
import com.kusl.myweather.data.remote.dto.PointResponseDto
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NwsMapperTest {

    private val json: Json = WeatherJson

    @Test
    fun `maps point response to metadata`() {
        val body = """
            {"properties":{"gridId":"AKQ","gridX":83,"gridY":61,
             "timeZone":"America/New_York",
             "relativeLocation":{"properties":{"city":"Norfolk","state":"VA"}}}}
        """.trimIndent()
        val dto = json.decodeFromString<PointResponseDto>(body)
        val meta = NwsMappers.toPointMetadata(GeoPoint(36.85, -76.28), dto)!!
        assertEquals("AKQ", meta.gridId)
        assertEquals(83, meta.gridX)
        assertEquals(61, meta.gridY)
        assertEquals("Norfolk, VA", meta.displayName)
        assertEquals("America/New_York", meta.timeZone)
    }

    @Test
    fun `point mapping returns null when grid identity missing`() {
        val dto = json.decodeFromString<PointResponseDto>("""{"properties":{"timeZone":"X"}}""")
        assertNull(NwsMappers.toPointMetadata(GeoPoint(0.0, 0.0), dto))
        assertNull(NwsMappers.toPointMetadata(GeoPoint(0.0, 0.0), null))
    }

    @Test
    fun `maps forecast periods and rounds precipitation`() {
        val body = """
            {"properties":{"generatedAt":"2026-06-19T12:00:00Z","updateTime":"2026-06-19T11:30:00Z",
             "periods":[
               {"number":1,"name":"This Afternoon","startTime":"2026-06-19T12:00:00-04:00",
                "endTime":"2026-06-19T18:00:00-04:00","isDaytime":true,"temperature":81,
                "temperatureUnit":"F","probabilityOfPrecipitation":{"unitCode":"wmoUnit:percent","value":36.4},
                "windSpeed":"7 mph","windDirection":"SW","icon":"https://x/y",
                "shortForecast":"Partly Sunny","detailedForecast":"Partly sunny, with a high near 81."}
             ]}}
        """.trimIndent()
        val dto = json.decodeFromString<ForecastResponseDto>(body)
        val forecast = NwsMappers.toForecast(GridPoint("AKQ", 83, 61), dto)!!
        assertEquals(1, forecast.periods.size)
        val p = forecast.current!!
        assertEquals("This Afternoon", p.name)
        assertEquals(81, p.temperature)
        assertEquals(36, p.probabilityOfPrecipitation) // 36.4 rounds to 36
        assertEquals("SW", p.windDirection)
    }

    @Test
    fun `null probability stays null`() {
        val body = """
            {"properties":{"periods":[
               {"number":1,"name":"Tonight","startTime":"","endTime":"","isDaytime":false,
                "temperature":60,"temperatureUnit":"F","probabilityOfPrecipitation":{"value":null},
                "windSpeed":"5 mph","windDirection":"N","icon":"","shortForecast":"Clear",
                "detailedForecast":"Clear."}]}}
        """.trimIndent()
        val dto = json.decodeFromString<ForecastResponseDto>(body)
        val forecast = NwsMappers.toForecast(GridPoint("AKQ", 1, 1), dto)!!
        assertNull(forecast.current!!.probabilityOfPrecipitation)
    }

    @Test
    fun `ignores unknown keys`() {
        // Real NWS payloads carry many extra fields; deserialization must not fail.
        val body = """{"properties":{"gridId":"BOX","gridX":1,"gridY":2,"unknownThing":{"a":1},"extra":[1,2,3]}}"""
        val dto = json.decodeFromString<PointResponseDto>(body)
        assertEquals("BOX", NwsMappers.toPointMetadata(GeoPoint(42.0, -71.0), dto)!!.gridId)
    }
}
