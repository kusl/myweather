package com.kusl.myweather.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.data.location.LocationSource
import com.kusl.myweather.data.location.LocationResult
import com.kusl.myweather.data.settings.SettingsRepository
import com.kusl.myweather.domain.AreaWeatherResult
import com.kusl.myweather.domain.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationSource,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun hasLocationPermission(): Boolean = locationProvider.hasPermission()

    /** Called after the runtime permission was denied by the user. */
    fun onPermissionDenied() {
        _uiState.update { it.copy(locationStatus = LocationStatus.PermissionDenied) }
    }

    /** Try to get a device fix and load the forecast for it. */
    fun requestDeviceLocation() {
        viewModelScope.launch {
            _uiState.update { it.copy(locationStatus = LocationStatus.Requesting) }
            when (val result = locationProvider.currentLocation()) {
                is LocationResult.Available -> {
                    _uiState.update { it.copy(locationStatus = LocationStatus.Idle) }
                    load(result.point)
                }
                LocationResult.PermissionDenied ->
                    _uiState.update { it.copy(locationStatus = LocationStatus.PermissionDenied) }
                LocationResult.Unavailable ->
                    _uiState.update { it.copy(locationStatus = LocationStatus.Unavailable) }
            }
        }
    }

    /** Load the forecast for free-form `"lat, lon"` text. */
    fun loadManual(text: String) {
        val point = GeoPoint.parse(text)
        if (point == null) {
            _uiState.update {
                it.copy(message = "That doesn't look like a valid \"latitude, longitude\".")
            }
            return
        }
        load(point)
    }

    fun load(point: GeoPoint) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, query = point, message = null, locationStatus = LocationStatus.Idle)
            }
            val radius = settingsRepository.neighborhoodRadius.first()
            when (val result = weatherRepository.getAreaWeather(point, radius)) {
                is AreaWeatherResult.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        area = result.area,
                        offline = result.area.fromCache,
                        message = null,
                    )
                }
                AreaWeatherResult.NoCoverage -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        area = null,
                        message = "The National Weather Service doesn't cover that spot. The NWS API is for U.S. locations only.",
                    )
                }
                is AreaWeatherResult.Unavailable -> _uiState.update {
                    it.copy(isLoading = false, message = result.message)
                }
            }
        }
    }

    fun refresh() {
        _uiState.value.query?.let { load(it) }
    }
}
