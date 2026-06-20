package com.kusl.myweather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kusl.myweather.data.local.dao.ForecastCacheDao
import com.kusl.myweather.data.local.dao.PointMetadataDao
import com.kusl.myweather.data.local.dao.SavedLocationDao
import com.kusl.myweather.data.local.entity.ForecastCacheEntity
import com.kusl.myweather.data.local.entity.PointMetadataEntity
import com.kusl.myweather.data.local.entity.SavedLocationEntity

@Database(
    entities = [
        PointMetadataEntity::class,
        ForecastCacheEntity::class,
        SavedLocationEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun pointMetadataDao(): PointMetadataDao
    abstract fun forecastCacheDao(): ForecastCacheDao
    abstract fun savedLocationDao(): SavedLocationDao

    companion object {
        const val NAME = "myweather.db"
    }
}
