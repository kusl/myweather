package com.kusl.myweather.domain.model

import com.kusl.myweather.core.GeoPoint

/** A user-saved location they want to monitor. */
data class SavedLocation(
    val id: Long,
    val label: String,
    val latitude: Double,
    val longitude: Double,
    val createdAt: Long,
) {
    val point: GeoPoint get() = GeoPoint(latitude, longitude)
}
