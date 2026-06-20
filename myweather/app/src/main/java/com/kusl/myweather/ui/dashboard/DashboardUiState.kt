package com.kusl.myweather.ui.dashboard

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.domain.model.AreaWeather

/** How a device-location attempt is currently going (drives the fallback UI). */
enum class LocationStatus { Idle, Requesting, PermissionDenied, Unavailable }

data class DashboardUiState(
    val isLoading: Boolean = false,
    val query: GeoPoint? = null,
    val area: AreaWeather? = null,
    /** True when the displayed forecast came from cache because NWS was unreachable. */
    val offline: Boolean = false,
    /** A user-facing message for the empty/error/no-coverage state. */
    val message: String? = null,
    val locationStatus: LocationStatus = LocationStatus.Idle,
) {
    val hasData: Boolean get() = area != null
}
