package com.kusl.myweather.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kusl.myweather.data.local.entity.ForecastCacheEntity
import com.kusl.myweather.data.local.entity.PointMetadataEntity
import com.kusl.myweather.data.local.entity.SavedLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PointMetadataDao {
    @Query("SELECT * FROM point_metadata WHERE cacheKey = :key LIMIT 1")
    suspend fun getByKey(key: String): PointMetadataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PointMetadataEntity)
}

@Dao
interface ForecastCacheDao {
    @Query("SELECT * FROM forecast_cache WHERE gridKey = :key LIMIT 1")
    suspend fun getByKey(key: String): ForecastCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ForecastCacheEntity)

    @Query("DELETE FROM forecast_cache WHERE expiresAtEpochMs < :cutoffEpochMs")
    suspend fun deleteExpiredBefore(cutoffEpochMs: Long)
}

@Dao
interface SavedLocationDao {
    @Query("SELECT * FROM saved_location ORDER BY createdAtEpochMs ASC")
    fun observeAll(): Flow<List<SavedLocationEntity>>

    @Query("SELECT * FROM saved_location ORDER BY createdAtEpochMs ASC")
    suspend fun getAllOnce(): List<SavedLocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SavedLocationEntity): Long

    @Delete
    suspend fun delete(entity: SavedLocationEntity)

    @Query("DELETE FROM saved_location WHERE id = :id")
    suspend fun deleteById(id: Long)
}
