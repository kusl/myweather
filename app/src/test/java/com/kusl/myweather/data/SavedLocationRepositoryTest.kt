package com.kusl.myweather.data

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.data.local.dao.SavedLocationDao
import com.kusl.myweather.data.local.entity.SavedLocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class SavedLocationRepositoryTest {

    @Test
    fun `addCurrentIfAbsent saves a new coordinate and dedupes by the 4dp NWS key`() = runTest {
        val repo = SavedLocationRepository(FakeSavedLocationDao())

        // First capture of a fresh coordinate is saved.
        val firstId = repo.addCurrentIfAbsent(GeoPoint(36.85, -76.28), "Norfolk, VA")
        assertNotNull(firstId)
        assertEquals(1, repo.getAllOnce().size)

        // A re-check within ~11 m rounds to the same 4-dp cache key, so it's skipped.
        val dupId = repo.addCurrentIfAbsent(GeoPoint(36.850049, -76.280049), "Norfolk again")
        assertNull(dupId)
        assertEquals(1, repo.getAllOnce().size)

        // A clearly different coordinate is saved as a second entry.
        val secondId = repo.addCurrentIfAbsent(GeoPoint(40.7128, -74.0060), "New York, NY")
        assertNotNull(secondId)
        assertEquals(2, repo.getAllOnce().size)
    }
}

/** In-memory [SavedLocationDao] with an auto-incrementing id, like Room's. */
private class FakeSavedLocationDao : SavedLocationDao {
    private val store = linkedMapOf<Long, SavedLocationEntity>()
    private var nextId = 1L

    override fun observeAll(): Flow<List<SavedLocationEntity>> =
        flowOf(store.values.sortedBy { it.createdAtEpochMs })

    override suspend fun getAllOnce(): List<SavedLocationEntity> =
        store.values.sortedBy { it.createdAtEpochMs }

    override suspend fun insert(entity: SavedLocationEntity): Long {
        val id = if (entity.id == 0L) nextId++ else entity.id
        store[id] = entity.copy(id = id)
        return id
    }

    override suspend fun delete(entity: SavedLocationEntity) {
        store.remove(entity.id)
    }

    override suspend fun deleteById(id: Long) {
        store.remove(id)
    }
}
