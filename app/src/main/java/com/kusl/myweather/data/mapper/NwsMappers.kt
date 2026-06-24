package com.kusl.myweather.data.mapper

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.core.GridPoint
import com.kusl.myweather.data.remote.dto.AlertsResponseDto
import com.kusl.myweather.data.remote.dto.ForecastResponseDto
import com.kusl.myweather.data.remote.dto.ObservationResponseDto
import com.kusl.myweather.data.remote.dto.PeriodDto
import com.kusl.myweather.data.remote.dto.PointResponseDto
import com.kusl.myweather.data.remote.dto.QuantitativeValueDto
import com.kusl.myweather.data.remote.dto.StationsResponseDto
import com.kusl.myweather.domain.model.CurrentObservation
import com.kusl.myweather.domain.model.Forecast
import com.kusl.myweather.domain.model.ForecastPeriod
import com.kusl.myweather.domain.model.PointMetadata
import com.kusl.myweather.domain.model.WeatherAlert
import kotlin.math.roundToInt

/**
 * Pure DTO -> domain mapping. No I/O, no Android — fully unit-testable. Returns
 * null when a required field (the grid identity) is missing, mirroring the
 * .NET client's "missing grid metadata" guard.
 */
object NwsMappers {

    fun toPointMetadata(query: GeoPoint, dto: PointResponseDto?): PointMetadata? {
        val props = dto?.properties ?: return null
        val gridId = props.gridId ?: return null
        val gridX = props.gridX ?: return null
        val gridY = props.gridY ?: return null
        val loc = props.relativeLocation?.properties
        return PointMetadata(
            queryLatitude = query.latitude,
            queryLongitude = query.longitude,
            gridId = gridId,
            gridX = gridX,
            gridY = gridY,
            city = loc?.city,
            state = loc?.state,
            timeZone = props.timeZone,
        )
    }

    fun toForecast(grid: GridPoint, dto: ForecastResponseDto?): Forecast? {
        val props = dto?.properties ?: return null
        return Forecast(
            gridId = grid.gridId,
            gridX = grid.gridX,
            gridY = grid.gridY,
            generatedAt = props.generatedAt,
            updateTime = props.updateTime,
            periods = (props.periods ?: emptyList()).map { it.toDomain() },
        )
    }

    /**
     * Map an `/alerts/active` FeatureCollection to domain alerts. An alert with
     * no `event` is dropped (nothing meaningful to show); the coded fields fall
     * back to "Unknown" and the human text is carried through verbatim. Sorted
     * most-severe first so the UI can surface the worst alert at the top.
     */
    fun toAlerts(dto: AlertsResponseDto?): List<WeatherAlert> {
        val features = dto?.features ?: return emptyList()
        return features.mapNotNull { feature ->
            val p = feature.properties ?: return@mapNotNull null
            val event = p.event ?: return@mapNotNull null
            WeatherAlert(
                id = p.id ?: feature.id ?: event,
                event = event,
                severity = p.severity ?: "Unknown",
                urgency = p.urgency ?: "Unknown",
                certainty = p.certainty ?: "Unknown",
                headline = p.headline,
                description = p.description.orEmpty(),
                instruction = p.instruction,
                areaDesc = p.areaDesc,
                onset = p.onset,
                expires = p.expires,
                ends = p.ends,
                senderName = p.senderName,
            )
        }.sortedBy { WeatherAlert.severityRank(it.severity) }
    }

    /** The first usable station identifier from a `/stations` response, or null. */
    fun firstStationId(dto: StationsResponseDto?): String? {
        val features = dto?.features ?: return null
        for (feature in features) {
            val id = feature.properties?.stationIdentifier?.takeIf { it.isNotBlank() }
                ?: feature.id?.substringAfterLast('/')?.takeIf { it.isNotBlank() }
            if (id != null) return id
        }
        return null
    }

    /**
     * Map a latest-observation payload to domain, converting NWS's SI units to
     * the app's US-customary display units. Conversions are unit-code aware and
     * default to the SI unit NWS actually sends (°C, km/h, Pa, metres). Returns
     * null when there's nothing measured to show.
     */
    fun toObservation(dto: ObservationResponseDto?, stationId: String?): CurrentObservation? {
        val p = dto?.properties ?: return null
        val observation = CurrentObservation(
            timestamp = p.timestamp,
            textDescription = p.textDescription?.takeIf { it.isNotBlank() },
            temperatureF = celsiusToFahrenheit(p.temperature)?.round(1),
            dewpointF = celsiusToFahrenheit(p.dewpoint)?.round(1),
            relativeHumidityPct = p.relativeHumidity?.value?.roundToInt(),
            windSpeedMph = toMph(p.windSpeed)?.round(1),
            windDirectionDeg = p.windDirection?.value?.roundToInt(),
            windGustMph = toMph(p.windGust)?.round(1),
            pressureInHg = toInchesOfMercury(p.barometricPressure ?: p.seaLevelPressure)?.round(2),
            visibilityMiles = toMiles(p.visibility)?.round(1),
            stationId = stationId,
        )
        return observation.takeUnless { it.isEmpty }
    }

    // ── unit conversions (defensive: inspect unitCode, fall back to NWS SI) ────

    private fun celsiusToFahrenheit(q: QuantitativeValueDto?): Double? {
        val v = q?.value ?: return null
        val unit = q.unitCode.orEmpty()
        return if (unit.contains("degF", ignoreCase = true)) v else v * 9.0 / 5.0 + 32.0
    }

    private fun toMph(q: QuantitativeValueDto?): Double? {
        val v = q?.value ?: return null
        val unit = q.unitCode.orEmpty()
        return when {
            unit.contains("mph", ignoreCase = true) || unit.contains("mi_h", ignoreCase = true) -> v
            unit.contains("m_s-1", ignoreCase = true) -> v * 2.236_936_292
            else -> v * 0.621_371_192 // km/h (NWS default: km_h-1) -> mph
        }
    }

    private fun toInchesOfMercury(q: QuantitativeValueDto?): Double? {
        val v = q?.value ?: return null
        val unit = q.unitCode.orEmpty()
        return when {
            unit.contains("inHg", ignoreCase = true) -> v
            unit.contains("hPa", ignoreCase = true) || unit.contains("mbar", ignoreCase = true) -> v / 33.863_886_667
            else -> v / 3386.388_666_7 // Pa (NWS default) -> inHg
        }
    }

    private fun toMiles(q: QuantitativeValueDto?): Double? {
        val v = q?.value ?: return null
        val unit = q.unitCode.orEmpty()
        return when {
            unit.contains("km", ignoreCase = true) -> v / 1.609_344
            unit.contains("mile", ignoreCase = true) -> v
            else -> v / 1609.344 // metres (NWS default) -> miles
        }
    }

    private fun Double.round(decimals: Int): Double {
        var factor = 1.0
        repeat(decimals) { factor *= 10 }
        return (this * factor).roundToInt() / factor
    }

    private fun PeriodDto.toDomain(): ForecastPeriod = ForecastPeriod(
        number = number,
        name = name.orEmpty(),
        startTime = startTime.orEmpty(),
        endTime = endTime.orEmpty(),
        isDaytime = isDaytime,
        temperature = temperature,
        temperatureUnit = temperatureUnit ?: "F",
        probabilityOfPrecipitation = probabilityOfPrecipitation?.value?.roundToInt(),
        windSpeed = windSpeed.orEmpty(),
        windDirection = windDirection.orEmpty(),
        shortForecast = shortForecast.orEmpty(),
        detailedForecast = detailedForecast.orEmpty(),
        icon = icon.orEmpty(),
    )
}
