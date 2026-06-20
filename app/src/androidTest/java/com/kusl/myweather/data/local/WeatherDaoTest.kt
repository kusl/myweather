package com.kusl.myweather.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kusl.myweather.data.local.entity.ForecastCacheEntity
import com.kusl.myweather.data.local.entity.PointMetadataEntity
import com.kusl.myweather.data.local.entity.SavedLocationEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test — requires a connected device or emulator. Exercises the
 * real Room/SQLite implementation (an in-memory database) end to end.
 *
 * Run with:  ./gradlew connectedDebugAndroidTest
 */
@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    private lateinit var db: WeatherDatabase

    @Before
    fun create() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java,
        ).allowMainThreadQueries().build()
    }

    @After
    fun close() = db.close()

    @Test
    fun forecastUpsertReplacesAndDeleteExpiredWorks() = runBlocking {
        val dao = db.forecastCacheDao()
        val base = ForecastCacheEntity(
            gridKey = "AKQ/83,61", gridId = "AKQ", gridX = 83, gridY = 61,
            payloadJson = "{}", etag = "v1", retrievedAtEpochMs = 0, expiresAtEpochMs = 100,
        )
        dao.upsert(base)
        dao.upsert(base.copy(etag = "v2", expiresAtEpochMs = 5_000))
        assertEquals("v2", dao.getByKey("AKQ/83,61")!!.etag) // REPLACE on conflict

        dao.deleteExpiredBefore(1_000) // expiresAt 5000 > 1000 => kept
        assertEquals("v2", dao.getByKey("AKQ/83,61")!!.etag)
        dao.deleteExpiredBefore(10_000) // now stale => removed
        assertNull(dao.getByKey("AKQ/83,61"))
    }

    @Test
    fun savedLocationsObserveReflectsInsertAndDelete() = runBlocking {
        val dao = db.savedLocationDao()
        val id = dao.insert(
            SavedLocationEntity(label = "Home", latitude = 36.85, longitude = -76.28, createdAtEpochMs = 1),
        )
        assertEquals(1, dao.observeAll().first().size)
        dao.deleteById(id)
        assertEquals(0, dao.observeAll().first().size)
    }

    @Test
    fun pointMetadataRoundTrips() = runBlocking {
        val dao = db.pointMetadataDao()
        dao.upsert(
            PointMetadataEntity(
                cacheKey = "36.8500,-76.2800", queryLatitude = 36.85, queryLongitude = -76.28,
                gridId = "AKQ", gridX = 83, gridY = 61, city = "Norfolk", state = "VA",
                timeZone = "America/New_York", retrievedAtEpochMs = 0, expiresAtEpochMs = 1,
            ),
        )
        assertEquals("Norfolk", dao.getByKey("36.8500,-76.2800")!!.city)
        assertNull(dao.getByKey("nope"))
    }
}
