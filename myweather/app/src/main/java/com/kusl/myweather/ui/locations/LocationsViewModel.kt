package com.kusl.myweather.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusl.myweather.data.SavedLocationRepository
import com.kusl.myweather.domain.model.SavedLocation
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LocationsViewModel(
    private val repository: SavedLocationRepository,
) : ViewModel() {

    val locations: StateFlow<List<SavedLocation>> =
        repository.observeAll().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    fun add(label: String, latitude: Double, longitude: Double) {
        viewModelScope.launch { repository.add(label, latitude, longitude) }
    }

    fun delete(id: Long) {
        viewModelScope.launch { repository.delete(id) }
    }
}
