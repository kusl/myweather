package com.kusl.myweather.data

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

    suspend fun delete(id: Long) = dao.deleteById(id)

    private fun SavedLocationEntity.toDomain() = SavedLocation(
        id = id,
        label = label,
        latitude = latitude,
        longitude = longitude,
        createdAt = createdAtEpochMs,
    )
}
