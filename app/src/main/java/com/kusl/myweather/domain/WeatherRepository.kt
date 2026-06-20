package com.kusl.myweather.domain

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.domain.model.AreaWeather

/** The outcome of an area-weather request, modelled so the UI can react cleanly. */
sealed interface AreaWeatherResult {
    /** Got data. [AreaWeather.fromCache] tells the UI whether it's a stale/offline copy. */
    data class Success(val area: AreaWeather) : AreaWeatherResult

    /** A valid coordinate that NWS simply doesn't provide a grid forecast for. */
    data object NoCoverage : AreaWeatherResult

    /** Network/server problem and nothing cached to fall back to. */
    data class Unavailable(val message: String) : AreaWeatherResult
}

interface WeatherRepository {
    /**
     * Resolve [point] to its grid, fetch the headline forecast for the user's
     * own cell, and assemble the surrounding [radius]-ring tile matrix —
     * cache-aside throughout, serving stale data when the network is down.
     */
    suspend fun getAreaWeather(point: GeoPoint, radius: Int): AreaWeatherResult
}
