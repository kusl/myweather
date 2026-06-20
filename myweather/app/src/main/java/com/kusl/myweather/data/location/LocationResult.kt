package com.kusl.myweather.data.location

import com.kusl.myweather.core.GeoPoint

/**
 * The outcome of a device-location request. Like the .NET dashboard's browser
 * bridge, the location call NEVER throws — every failure mode is a typed result
 * that maps to a distinct UI state, and manual entry is always available.
 */
sealed interface LocationResult {
    data class Available(val point: GeoPoint) : LocationResult

    /** The user declined the runtime permission. */
    data object PermissionDenied : LocationResult

    /** Location services are off, no provider, or no fix could be obtained. */
    data object Unavailable : LocationResult
}
