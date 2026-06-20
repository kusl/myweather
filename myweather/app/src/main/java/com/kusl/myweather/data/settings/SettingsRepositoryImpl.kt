package com.kusl.myweather.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kusl.myweather.data.remote.UserAgentProvider
import com.kusl.myweather.data.settings.SettingsRepository.Companion.DEFAULT_RADIUS
import com.kusl.myweather.data.settings.SettingsRepository.Companion.MAX_RADIUS
import com.kusl.myweather.data.settings.SettingsRepository.Companion.MIN_RADIUS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Preferences DataStore-backed settings: the NWS User-Agent (headline
 * privacy/rate-limit control) and the neighborhood radius (matrix size).
 */
class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    override val userAgent: Flow<String> = context.settingsDataStore.data.map { prefs ->
        prefs[KEY_USER_AGENT]?.takeIf { it.isNotBlank() } ?: UserAgentProvider.DEFAULT
    }

    override val neighborhoodRadius: Flow<Int> = context.settingsDataStore.data.map { prefs ->
        (prefs[KEY_RADIUS] ?: DEFAULT_RADIUS).coerceIn(MIN_RADIUS, MAX_RADIUS)
    }

    override suspend fun setUserAgent(value: String) {
        context.settingsDataStore.edit { it[KEY_USER_AGENT] = value.trim() }
    }

    override suspend fun setNeighborhoodRadius(value: Int) {
        context.settingsDataStore.edit { it[KEY_RADIUS] = value.coerceIn(MIN_RADIUS, MAX_RADIUS) }
    }

    private companion object {
        val KEY_USER_AGENT = stringPreferencesKey("nws_user_agent")
        val KEY_RADIUS = intPreferencesKey("neighborhood_radius")
    }
}
