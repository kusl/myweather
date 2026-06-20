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
 */
data class AreaWeather(
    val query: GeoPoint,
    val metadata: PointMetadata,
    val primary: Forecast,
    val tiles: List<WeatherTile>,
    val fromCache: Boolean,
)
