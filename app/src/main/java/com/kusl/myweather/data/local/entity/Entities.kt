package com.kusl.myweather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Long-lived `coordinate -> grid` mapping. Keyed by the 4-dp-rounded coordinate
 * string so physically-close lookups share one row. This mapping effectively
 * never changes, so its TTL is measured in weeks.
 */
@Entity(tableName = "point_metadata")
data class PointMetadataEntity(
    @PrimaryKey val cacheKey: String,
    val queryLatitude: Double,
    val queryLongitude: Double,
    val gridId: String,
    val gridX: Int,
    val gridY: Int,
    val city: String?,
    val state: String?,
    val timeZone: String?,
    val retrievedAtEpochMs: Long,
    val expiresAtEpochMs: Long,
)

/**
 * Short-lived forecast for a grid cell. The forecast itself is stored as a
 * serialised JSON blob; the columns alongside it drive freshness:
 *  - [expiresAtEpochMs] from NWS Cache-Control max-age (clamped), and
 *  - [etag] for conditional `If-None-Match` revalidation.
 */
@Entity(tableName = "forecast_cache")
data class ForecastCacheEntity(
    @PrimaryKey val gridKey: String,
    val gridId: String,
    val gridX: Int,
    val gridY: Int,
    val payloadJson: String,
    val etag: String?,
    val retrievedAtEpochMs: Long,
    val expiresAtEpochMs: Long,
)

/** A location the user chose to save and monitor. */
@Entity(tableName = "saved_location")
data class SavedLocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val label: String,
    val latitude: Double,
    val longitude: Double,
    val createdAtEpochMs: Long,
)

/**
 * The universal transport-level HTTP cache, written by [com.kusl.myweather.data.remote.HttpCacheInterceptor].
 *
 * One row per resolved request, keyed by `"$method $url"` so that any two calls
 * which resolve to the same endpoint collapse onto a single row (request
 * de-duplication). The raw response body is stored verbatim as text — NWS speaks
 * JSON/GeoJSON, which is UTF-8 — together with the status, `ETag`, content type,
 * and the computed expiry that governs freshness. Error statuses (notably the
 * cacheable `404`s NWS returns) are stored here too.
 */
@Entity(tableName = "http_cache")
data class HttpCacheEntity(
    /** `"$method $url"` — the de-duplication key. */
    @PrimaryKey val cacheKey: String,
    val url: String,
    val method: String,
    val statusCode: Int,
    val statusMessage: String,
    val contentType: String?,
    val body: String,
    val etag: String?,
    val retrievedAtEpochMs: Long,
    val expiresAtEpochMs: Long,
)
