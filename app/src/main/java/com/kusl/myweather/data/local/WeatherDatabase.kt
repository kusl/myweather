package com.kusl.myweather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kusl.myweather.data.local.dao.ForecastCacheDao
import com.kusl.myweather.data.local.dao.HttpCacheDao
import com.kusl.myweather.data.local.dao.PointMetadataDao
import com.kusl.myweather.data.local.dao.SavedLocationDao
import com.kusl.myweather.data.local.entity.ForecastCacheEntity
import com.kusl.myweather.data.local.entity.HttpCacheEntity
import com.kusl.myweather.data.local.entity.PointMetadataEntity
import com.kusl.myweather.data.local.entity.SavedLocationEntity

/**
 * Schema history:
 *  - v1: point_metadata, forecast_cache, saved_location
 *  - v2: + http_cache (universal transport cache; see HttpCacheInterceptor)
 *
 * This is a pure cache database, so [com.kusl.myweather.di.AppContainer] opens it
 * with `fallbackToDestructiveMigration` — on a version bump the old data is
 * dropped and transparently re-fetched from NWS. No hand-written migration is
 * needed; the exported schema JSON is committed for review/CI.
 */
@Database(
    entities = [
        PointMetadataEntity::class,
        ForecastCacheEntity::class,
        SavedLocationEntity::class,
        HttpCacheEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun pointMetadataDao(): PointMetadataDao
    abstract fun forecastCacheDao(): ForecastCacheDao
    abstract fun savedLocationDao(): SavedLocationDao
    abstract fun httpCacheDao(): HttpCacheDao

    companion object {
        const val NAME = "myweather.db"
    }
}
