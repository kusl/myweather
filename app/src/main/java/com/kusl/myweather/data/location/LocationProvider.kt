package com.kusl.myweather.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.CancellationSignal
import androidx.core.content.ContextCompat
import com.kusl.myweather.core.GeoPoint
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Obtains a one-shot device fix using the Android framework [LocationManager]
 * only — deliberately NO Google Play Services / FusedLocationProvider, in
 * keeping with the zero-Google-dependency, privacy-first goal.
 *
 * Returns a typed [LocationResult]; it never throws for the "no permission" or
 * "no fix" cases, so callers always have a clean fallback to manual entry.
 */
class LocationProvider(private val context: Context) : LocationSource {

    private val locationManager: LocationManager?
        get() = ContextCompat.getSystemService(context, LocationManager::class.java)

    override fun hasPermission(): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

    override suspend fun currentLocation(): LocationResult {
        if (!hasPermission()) return LocationResult.PermissionDenied
        val manager = locationManager ?: return LocationResult.Unavailable
        if (!manager.isLocationEnabled) return LocationResult.Unavailable

        val provider = bestProvider(manager) ?: return LocationResult.Unavailable

        // Try a fresh single fix first (API 30+), then fall back to last-known.
        val fresh = requestSingleFix(manager, provider)
        if (fresh != null) return LocationResult.Available(fresh)

        val last = runCatching { manager.getLastKnownLocation(provider) }.getOrNull()
        return if (last != null && GeoPoint.isValid(last.latitude, last.longitude)) {
            LocationResult.Available(GeoPoint(last.latitude, last.longitude))
        } else {
            LocationResult.Unavailable
        }
    }

    private fun bestProvider(manager: LocationManager): String? = when {
        manager.isProviderEnabled(LocationManager.FUSED_PROVIDER) -> LocationManager.FUSED_PROVIDER
        manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
        manager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
        else -> null
    }

    @Suppress("MissingPermission") // hasPermission() is checked before we get here.
    private suspend fun requestSingleFix(
        manager: LocationManager,
        provider: String,
    ): GeoPoint? = suspendCancellableCoroutine { cont ->
        val signal = CancellationSignal()
        cont.invokeOnCancellation { runCatching { signal.cancel() } }
        runCatching {
            manager.getCurrentLocation(
                provider,
                signal,
                context.mainExecutor,
            ) { location ->
                val point = location
                    ?.takeIf { GeoPoint.isValid(it.latitude, it.longitude) }
                    ?.let { GeoPoint(it.latitude, it.longitude) }
                if (cont.isActive) cont.resume(point)
            }
        }.onFailure {
            if (cont.isActive) cont.resume(null)
        }
    }
}
