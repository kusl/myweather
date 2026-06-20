package com.kusl.myweather.data.location

/**
 * Device-location seam. An interface so the dashboard ViewModel can be tested
 * without Android; [LocationProvider] is the framework-backed implementation.
 */
interface LocationSource {
    fun hasPermission(): Boolean
    suspend fun currentLocation(): LocationResult
}
