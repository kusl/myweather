package com.kusl.myweather.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusl.myweather.data.remote.UserAgentProvider
import com.kusl.myweather.data.settings.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settings: SettingsRepository,
) : ViewModel() {

    val userAgent: StateFlow<String> = settings.userAgent.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserAgentProvider.DEFAULT,
    )

    val neighborhoodRadius: StateFlow<Int> = settings.neighborhoodRadius.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingsRepository.DEFAULT_RADIUS,
    )

    fun setUserAgent(value: String) {
        viewModelScope.launch { settings.setUserAgent(value) }
    }

    fun setNeighborhoodRadius(value: Int) {
        viewModelScope.launch { settings.setNeighborhoodRadius(value) }
    }
}
