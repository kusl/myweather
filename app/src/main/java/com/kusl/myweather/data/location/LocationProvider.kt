package com.kusl.myweather.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.CancellationSignal
import androidx.core.content.ContextCompat
import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.core.Telemetry
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

/**
 * Obtains a one-shot device fix using the Android framework [LocationManager]
 * only — deliberately NO Google Play Services / FusedLocationProvider, in
 * keeping with the zero-Google-dependency, privacy-first goal.
 *
 * Returns a typed [LocationResult]; it never throws for the "no permission" or
 * "no fix" cases, so callers always have a clean fallback to manual entry.
 *
 * A fresh fix is bounded by [FIX_TIMEOUT_MS]: if the provider can't deliver one
 * in time (common on a cold start or indoors), we fall back to the last known
 * location and finally to [LocationResult.Unavailable], rather than leaving the
 * caller — and the on-screen spinner — waiting indefinitely.
 */
class LocationProvider(private val context: Context) : LocationSource {

    private val locationManager: LocationManager?
        get() = ContextCompat.getSystemService(context, LocationManager::class.java)

    override fun hasPermission(): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

    @Suppress("MissingPermission") // hasPermission() is checked at the top.
    override suspend fun currentLocation(): LocationResult {
        if (!hasPermission()) {
            Telemetry.w("Location", "no permission")
            return LocationResult.PermissionDenied
        }
        val manager = locationManager
        if (manager == null) {
            Telemetry.w("Location", "no LocationManager")
            return LocationResult.Unavailable
        }
        if (!manager.isLocationEnabled) {
            Telemetry.w("Location", "location services disabled")
            return LocationResult.Unavailable
        }

        val provider = bestProvider(manager)
        if (provider == null) {
            Telemetry.w("Location", "no enabled provider")
            return LocationResult.Unavailable
        }
        Telemetry.i("Location", "requesting fix via $provider")

        // Try a fresh single fix first (API 30+), bounded by a timeout; then
        // fall back to last-known so we don't hang on a cold/indoor start.
        val fresh = withTimeoutOrNull(FIX_TIMEOUT_MS) { requestSingleFix(manager, provider) }
        if (fresh != null) {
            Telemetry.i("Location", "fresh fix ${fresh.toApiString()}")
            return LocationResult.Available(fresh)
        }
        Telemetry.w("Location", "no fresh fix within ${FIX_TIMEOUT_MS}ms; trying last-known")

        val last = runCatching { manager.getLastKnownLocation(provider) }.getOrNull()
        return if (last != null && GeoPoint.isValid(last.latitude, last.longitude)) {
            val point = GeoPoint(last.latitude, last.longitude)
            Telemetry.i("Location", "last-known ${point.toApiString()}")
            LocationResult.Available(point)
        } else {
            Telemetry.w("Location", "no location available")
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

    private companion object {
        /** Upper bound on how long we wait for a fresh single fix before falling back. */
        const val FIX_TIMEOUT_MS = 10_000L
    }
}
