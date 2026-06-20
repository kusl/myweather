package com.kusl.myweather.data.settings

import kotlinx.coroutines.flow.Flow

/**
 * User settings seam. An interface so ViewModels can be unit-tested with a
 * trivial fake; the production implementation is [SettingsRepositoryImpl].
 */
interface SettingsRepository {
    val userAgent: Flow<String>
    val neighborhoodRadius: Flow<Int>
    suspend fun setUserAgent(value: String)
    suspend fun setNeighborhoodRadius(value: Int)

    companion object {
        const val DEFAULT_RADIUS = 1
        const val MIN_RADIUS = 1
        const val MAX_RADIUS = 3
    }
}
