package com.kusl.myweather.data

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.data.local.dao.SavedLocationDao
import com.kusl.myweather.data.local.entity.SavedLocationEntity
import com.kusl.myweather.domain.model.SavedLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Thin repository over the saved-locations table, mapping entity <-> domain. */
class SavedLocationRepository(private val dao: SavedLocationDao) {

    fun observeAll(): Flow<List<SavedLocation>> =
        dao.observeAll().map { rows -> rows.map { it.toDomain() } }

    suspend fun getAllOnce(): List<SavedLocation> = dao.getAllOnce().map { it.toDomain() }

    suspend fun add(label: String, latitude: Double, longitude: Double): Long =
        dao.insert(
            SavedLocationEntity(
                label = label.ifBlank { "%.4f, %.4f".format(latitude, longitude) },
                latitude = latitude,
                longitude = longitude,
                createdAtEpochMs = System.currentTimeMillis(),
            ),
        )

    /**
     * Save [point] under [label] unless an entry already exists at the same
     * coordinate (compared at the NWS 4-dp granularity, ~11 m). This lets the
     * dashboard auto-capture a "use my location" fix without piling up
     * duplicates when the user re-checks the same place. Returns the new row id,
     * or null when a matching location was already saved.
     */
    suspend fun addCurrentIfAbsent(point: GeoPoint, label: String): Long? {
        val key = point.toCacheKey()
        val alreadySaved = dao.getAllOnce().any { row ->
            GeoPoint.isValid(row.latitude, row.longitude) &&
                GeoPoint(row.latitude, row.longitude).toCacheKey() == key
        }
        if (alreadySaved) return null
        return add(label, point.latitude, point.longitude)
    }

    suspend fun delete(id: Long) = dao.deleteById(id)

    private fun SavedLocationEntity.toDomain() = SavedLocation(
        id = id,
        label = label,
        latitude = latitude,
        longitude = longitude,
        createdAt = createdAtEpochMs,
    )
}
