package com.kusl.myweather.domain.model

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.core.GridPoint
import kotlinx.serialization.Serializable

/** A single NWS forecast period (one row of the multi-day forecast). */
@Serializable
data class ForecastPeriod(
    val number: Int,
    val name: String,
    val startTime: String,
    val endTime: String,
    val isDaytime: Boolean,
    val temperature: Int,
    val temperatureUnit: String,
    val probabilityOfPrecipitation: Int?,
    val windSpeed: String,
    val windDirection: String,
    val shortForecast: String,
    val detailedForecast: String,
    val icon: String,
)

/**
 * The forecast for a single grid cell. Stored verbatim (serialised) in the cache
 * and rendered as one tile of the neighborhood matrix.
 */
@Serializable
data class Forecast(
    val gridId: String,
    val gridX: Int,
    val gridY: Int,
    val generatedAt: String?,
    val updateTime: String?,
    val periods: List<ForecastPeriod>,
) {
    val grid: GridPoint get() = GridPoint(gridId, gridX, gridY)

    /** The first/headline period (e.g. "This Afternoon"), or null if empty. */
    val current: ForecastPeriod? get() = periods.firstOrNull()
}

/** Resolved `coordinate -> grid` metadata from the NWS `/points` endpoint. */
@Serializable
data class PointMetadata(
    val queryLatitude: Double,
    val queryLongitude: Double,
    val gridId: String,
    val gridX: Int,
    val gridY: Int,
    val city: String?,
    val state: String?,
    val timeZone: String?,
) {
    val grid: GridPoint get() = GridPoint(gridId, gridX, gridY)
    val query: GeoPoint get() = GeoPoint(queryLatitude, queryLongitude)
    val displayName: String
        get() = when {
            city != null && state != null -> "$city, $state"
            city != null -> city
            else -> grid.toString()
        }
}

/**
 * A single active NWS watch/warning/advisory (Common Alerting Protocol).
 *
 * This is life-and-safety information, so the human-authored text fields
 * ([headline], [description], [instruction]) are carried through **verbatim** —
 * never summarised or reformatted — and surfaced prominently in the UI. The
 * coded fields ([severity], [urgency], [certainty]) keep the raw NWS strings so
 * nothing is lost in translation.
 *
 * Field reference: https://www.weather.gov/documentation/services-web-api
 */
@Serializable
data class WeatherAlert(
    /** Stable identifier (NWS `id`), used for de-duplication and list keys. */
    val id: String,
    /** e.g. "Tornado Warning", "Winter Storm Warning". */
    val event: String,
    /** Raw NWS value: Extreme | Severe | Moderate | Minor | Unknown. */
    val severity: String,
    /** Raw NWS value: Immediate | Expected | Future | Past | Unknown. */
    val urgency: String,
    /** Raw NWS value: Observed | Likely | Possible | Unlikely | Unknown. */
    val certainty: String,
    /** One-line headline (often includes timing). */
    val headline: String?,
    /** The full "what/where/when/impacts" narrative — shown verbatim. */
    val description: String,
    /** Protective-action guidance — shown verbatim. May be absent for ended alerts. */
    val instruction: String?,
    /** Human-readable affected area, e.g. "Norfolk, VA; Virginia Beach, VA". */
    val areaDesc: String?,
    /** ISO-8601 timestamps straight from NWS (rendered as-is by the UI). */
    val onset: String?,
    val expires: String?,
    val ends: String?,
    /** Issuing office, e.g. "NWS Wakefield VA". */
    val senderName: String?,
) {
    companion object {
        /**
         * Lower = more urgent, for sorting/colouring. Unknown severity sorts
         * after the named levels so a concrete "Severe" always wins the top slot.
         */
        fun severityRank(severity: String): Int = when (severity.trim().lowercase()) {
            "extreme" -> 0
            "severe" -> 1
            "moderate" -> 2
            "minor" -> 3
            else -> 4 // "unknown" / unrecognised
        }
    }
}

/**
 * The latest surface observation for the station nearest a location — the real,
 * *measured* conditions right now, as opposed to the forecast. Values are
 * normalised to the app's US-customary display units in the mapper (NWS reports
 * observations in SI: °C, km/h, Pa, metres).
 */
@Serializable
data class CurrentObservation(
    /** ISO-8601 observation time from NWS. */
    val timestamp: String?,
    /** Plain-language summary, e.g. "Mostly Cloudy". */
    val textDescription: String?,
    val temperatureF: Double?,
    val dewpointF: Double?,
    val relativeHumidityPct: Int?,
    val windSpeedMph: Double?,
    val windDirectionDeg: Int?,
    val windGustMph: Double?,
    val pressureInHg: Double?,
    val visibilityMiles: Double?,
    /** The reporting station, e.g. "KAKQ". */
    val stationId: String?,
) {
    /** True when every measured value is missing (so the UI can skip the card). */
    val isEmpty: Boolean
        get() = textDescription == null && temperatureF == null && dewpointF == null &&
            relativeHumidityPct == null && windSpeedMph == null && windGustMph == null &&
            pressureInHg == null && visibilityMiles == null
}

/**
 * The set of NWS endpoints and zone identifiers available for a location,
 * discovered from the `/points` response. Surfaced (read-only) so a curious user
 * can see exactly which official sources back the data they're looking at — and
 * follow them. Only populated on a fresh (online) `/points` resolution.
 */
data class LocationSources(
    val forecastOffice: String?,
    val radarStation: String?,
    val forecastZone: String?,
    val county: String?,
    val fireWeatherZone: String?,
    val forecastUrl: String?,
    val hourlyForecastUrl: String?,
    val gridDataUrl: String?,
    val observationStationsUrl: String?,
)

/** One tile in the rendered matrix: a cell plus how far it is from the user. */
data class WeatherTile(
    val grid: GridPoint,
    val forecast: Forecast?,
    val isPrimary: Boolean,
    val distanceMeters: Double?,
)

/**
 * Everything the dashboard needs for one location: the resolved metadata, the
 * user's own cell, and the surrounding tile matrix. [fromCache] flags that this
 * was served from the local cache because the network was unavailable.
 *
 * The supplementary fields ([alerts], [hourly], [observation], [sources]) are
 * the "as much information as we can get" extras for the *primary* location.
 * They default to empty so the grid-recenter path (which has no coordinate, and
 * is an ephemeral peek at a neighbouring cell) can omit them, and so older call
 * sites and tests keep compiling.
 */
data class AreaWeather(
    val query: GeoPoint,
    val metadata: PointMetadata,
    val primary: Forecast,
    val tiles: List<WeatherTile>,
    val fromCache: Boolean,
    val alerts: List<WeatherAlert> = emptyList(),
    val hourly: List<ForecastPeriod> = emptyList(),
    val observation: CurrentObservation? = null,
    val sources: LocationSources? = null,
)
