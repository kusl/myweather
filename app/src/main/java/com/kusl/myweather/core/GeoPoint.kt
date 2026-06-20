package com.kusl.myweather.core

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A validated WGS84 latitude/longitude pair.
 *
 * Ported from the sibling .NET dashboard's `GeoCoordinate`: the NWS API rounds
 * coordinates to four decimal places (~11 m), so we round to 4 dp for cache keys
 * — that collapses many physically-close inputs onto a single cached
 * `coordinate -> grid` mapping and sharply reduces calls to `/points`.
 */
data class GeoPoint(val latitude: Double, val longitude: Double) {

    init {
        require(!latitude.isNaN() && latitude in -90.0..90.0) {
            "Latitude must be between -90 and 90 degrees (was $latitude)."
        }
        require(!longitude.isNaN() && longitude in -180.0..180.0) {
            "Longitude must be between -180 and 180 degrees (was $longitude)."
        }
    }

    /** Round to [decimals] decimal places — the cache-key normalisation step. */
    fun rounded(decimals: Int = 4): GeoPoint {
        val factor = Math.pow(10.0, decimals.toDouble())
        fun r(v: Double) = Math.round(v * factor) / factor
        return GeoPoint(r(latitude), r(longitude))
    }

    /** Invariant `"lat,lon"` string in the exact shape NWS expects (max 4 dp). */
    fun toApiString(): String = "%.4f,%.4f".format(java.util.Locale.US, latitude, longitude)

    /** Stable cache key (identical to the API string). */
    fun toCacheKey(): String = rounded().toApiString()

    /** Great-circle (haversine) distance in metres — used to order neighbour cells. */
    fun distanceMetersTo(other: GeoPoint): Double {
        val earthRadiusMeters = 6_371_000.0
        val degToRad = Math.PI / 180.0
        val lat1 = latitude * degToRad
        val lat2 = other.latitude * degToRad
        val dLat = (other.latitude - latitude) * degToRad
        val dLon = (other.longitude - longitude) * degToRad
        val sinLat = sin(dLat / 2)
        val sinLon = sin(dLon / 2)
        val a = sinLat * sinLat + cos(lat1) * cos(lat2) * sinLon * sinLon
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadiusMeters * c
    }

    override fun toString(): String = toApiString()

    companion object {
        /** Validation that never throws — handy for guarding manual user input. */
        fun isValid(latitude: Double, longitude: Double): Boolean =
            !latitude.isNaN() && latitude in -90.0..90.0 &&
                !longitude.isNaN() && longitude in -180.0..180.0

        /** Parse a free-form `"lat, lon"` string; returns null if it isn't a valid pair. */
        fun parse(text: String): GeoPoint? {
            val parts = text.split(',', ' ').map { it.trim() }.filter { it.isNotEmpty() }
            if (parts.size != 2) return null
            val lat = parts[0].toDoubleOrNull() ?: return null
            val lon = parts[1].toDoubleOrNull() ?: return null
            return if (isValid(lat, lon)) GeoPoint(lat, lon) else null
        }
    }
}

internal fun Double.roundToIntHalfUp(): Int = this.roundToInt()
