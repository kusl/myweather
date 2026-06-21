package com.kusl.myweather.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.core.GridPoint
import com.kusl.myweather.core.Telemetry
import com.kusl.myweather.data.SavedLocationRepository
import com.kusl.myweather.data.location.LocationResult
import com.kusl.myweather.data.location.LocationSource
import com.kusl.myweather.data.settings.SettingsRepository
import com.kusl.myweather.domain.AreaWeatherResult
import com.kusl.myweather.domain.WeatherRepository
import com.kusl.myweather.domain.model.PointMetadata
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationSource,
    private val settingsRepository: SettingsRepository,
    private val savedLocationRepository: SavedLocationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    // One-shot toast signals. extraBufferCapacity lets us emit from non-suspend
    // code via tryEmit without dropping events to an attached collector.
    private val _events = MutableSharedFlow<DashboardEvent>(extraBufferCapacity = 16)
    val events: SharedFlow<DashboardEvent> = _events.asSharedFlow()

    // What the current view is anchored to, so refresh() reloads the right thing:
    //  - a tapped grid cell (set by recenterOnGrid), or
    //  - otherwise the coordinate in uiState.query (set by load()).
    private var anchorGrid: GridPoint? = null

    // Metadata of the currently displayed area; supplies the office time-zone to
    // the grid-recenter path and lets us ignore a tap on the current centre.
    private var lastMetadata: PointMetadata? = null

    fun hasLocationPermission(): Boolean = locationProvider.hasPermission()

    /** Called after the runtime permission was denied by the user. */
    fun onPermissionDenied() {
        Telemetry.w("Dashboard", "location permission denied by user")
        _uiState.update { it.copy(locationStatus = LocationStatus.PermissionDenied) }
    }

    /** Try to get a device fix and load the forecast for it. */
    fun requestDeviceLocation() {
        viewModelScope.launch {
            _uiState.update { it.copy(locationStatus = LocationStatus.Requesting) }
            Telemetry.i("Dashboard", "device location requested")
            toast("Finding your location\u2026")
            when (val result = locationProvider.currentLocation()) {
                is LocationResult.Available -> {
                    _uiState.update { it.copy(locationStatus = LocationStatus.Idle) }
                    // Persist a device fix to the saved list (deduped) once the
                    // forecast loads, so it picks up the resolved place name.
                    load(result.point, saveToList = true)
                }
                LocationResult.PermissionDenied -> {
                    _uiState.update { it.copy(locationStatus = LocationStatus.PermissionDenied) }
                    toast("Location permission denied")
                }
                LocationResult.Unavailable -> {
                    _uiState.update { it.copy(locationStatus = LocationStatus.Unavailable) }
                    toast("Couldn't get a location fix")
                }
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
            toast("Enter coordinates like \"36.85, -76.28\"")
            return
        }
        load(point)
    }

    /**
     * Load the forecast for [point]. When [saveToList] is true (the "use my
     * location" path) and the load succeeds, the coordinate is also added to the
     * saved-locations list — deduped, and labelled with the resolved place name.
     * Manual entry and re-opening a saved location pass false, so they never
     * auto-save.
     */
    fun load(point: GeoPoint, saveToList: Boolean = false) {
        viewModelScope.launch {
            anchorGrid = null
            _uiState.update {
                it.copy(isLoading = true, query = point, message = null, locationStatus = LocationStatus.Idle)
            }
            Telemetry.i("Dashboard", "load ${point.toApiString()}")
            val radius = settingsRepository.neighborhoodRadius.first()
            when (val result = weatherRepository.getAreaWeather(point, radius)) {
                is AreaWeatherResult.Success -> {
                    lastMetadata = result.area.metadata
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            area = result.area,
                            offline = result.area.fromCache,
                            message = null,
                        )
                    }
                    toast(if (result.area.fromCache) OFFLINE_TOAST else "Weather updated")
                    if (saveToList) saveCurrentLocation(point, result.area.metadata.displayName)
                }
                AreaWeatherResult.NoCoverage -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            area = null,
                            message = "The National Weather Service doesn't cover that spot. The NWS API is for U.S. locations only.",
                        )
                    }
                    toast("No NWS coverage there (U.S. only)")
                }
                is AreaWeatherResult.Unavailable -> {
                    _uiState.update { it.copy(isLoading = false, message = result.message) }
                    toast(result.message)
                }
            }
        }
    }

    /**
     * Make a tapped neighbouring cell the new centre of the matrix, in one tap.
     * No-op if we don't have an area yet, or if the tapped cell is already the
     * centre. The user can always return to a saved location from the Locations
     * tab, so this re-centring is intentionally ephemeral.
     */
    fun recenterOnGrid(grid: GridPoint) {
        val meta = lastMetadata ?: return
        if (grid == meta.grid) return
        anchorGrid = grid
        Telemetry.i("Dashboard", "recenter requested -> $grid")
        toast("Centering on $grid\u2026")
        reloadGrid(grid, meta.timeZone)
    }

    fun refresh() {
        val grid = anchorGrid
        val meta = lastMetadata
        if (grid != null && meta != null) {
            reloadGrid(grid, meta.timeZone)
        } else {
            _uiState.value.query?.let { load(it) }
        }
    }

    private fun reloadGrid(grid: GridPoint, timeZone: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = null, locationStatus = LocationStatus.Idle) }
            val radius = settingsRepository.neighborhoodRadius.first()
            when (val result = weatherRepository.getAreaWeatherForGrid(grid, radius, RECENTER_LABEL, timeZone)) {
                is AreaWeatherResult.Success -> {
                    lastMetadata = result.area.metadata
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            area = result.area,
                            offline = result.area.fromCache,
                            message = null,
                        )
                    }
                    toast(if (result.area.fromCache) OFFLINE_TOAST else "Weather updated")
                }
                AreaWeatherResult.NoCoverage -> {
                    _uiState.update { it.copy(isLoading = false, message = "There's no forecast for that grid cell.") }
                    toast("No forecast for that cell")
                }
                is AreaWeatherResult.Unavailable -> {
                    _uiState.update { it.copy(isLoading = false, message = result.message) }
                    toast(result.message)
                }
            }
        }
    }

    /**
     * Add the just-resolved current location to the saved list, deduped against
     * what's already stored (so repeated "use my location" taps don't pile up).
     * Uses the NWS-resolved place name (e.g. "Norfolk, VA") as the label, falling
     * back to a generic one if NWS didn't return a city/state.
     */
    private suspend fun saveCurrentLocation(point: GeoPoint, displayName: String?) {
        val label = displayName?.takeIf { it.isNotBlank() } ?: DEFAULT_SAVED_LABEL
        val newId = savedLocationRepository.addCurrentIfAbsent(point, label)
        if (newId != null) {
            Telemetry.i("Dashboard", "auto-saved current location '$label'")
            toast("Saved \u201C$label\u201D to your locations")
        } else {
            Telemetry.d("Dashboard", "current location already saved; not duplicating")
        }
    }

    private fun toast(text: String) {
        _events.tryEmit(DashboardEvent.Message(text))
    }

    private companion object {
        const val RECENTER_LABEL = "Selected grid cell"
        const val OFFLINE_TOAST = "Showing saved data \u2014 offline"
        const val DEFAULT_SAVED_LABEL = "My location"
    }
}
