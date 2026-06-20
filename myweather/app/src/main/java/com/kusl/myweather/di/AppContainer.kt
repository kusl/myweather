package com.kusl.myweather.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import com.kusl.myweather.data.SavedLocationRepository
import com.kusl.myweather.data.WeatherRepositoryImpl
import com.kusl.myweather.data.local.WeatherDatabase
import com.kusl.myweather.data.location.LocationProvider
import com.kusl.myweather.data.remote.NetworkModule
import com.kusl.myweather.data.remote.NwsApi
import com.kusl.myweather.data.remote.UserAgentProvider
import com.kusl.myweather.data.settings.SettingsRepository
import com.kusl.myweather.data.settings.SettingsRepositoryImpl
import com.kusl.myweather.domain.WeatherRepository
import com.kusl.myweather.ui.dashboard.DashboardViewModel
import com.kusl.myweather.ui.locations.LocationsViewModel
import com.kusl.myweather.ui.settings.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Manual dependency-injection container — built once in [com.kusl.myweather.MyWeatherApp]
 * and shared for the app's lifetime. We deliberately avoid a DI framework: the
 * graph is small, the wiring is explicit and easy to read, and it keeps the
 * dependency surface (and APK) minimal — in keeping with the project's ethos.
 */
class AppContainer(context: Context) {

    private val appContext = context.applicationContext
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val database: WeatherDatabase =
        Room.databaseBuilder(appContext, WeatherDatabase::class.java, WeatherDatabase.NAME)
            .fallbackToDestructiveMigration(dropAllTables = true) // cache DB; safe to rebuild on schema change
            .build()

    val settingsRepository: SettingsRepository = SettingsRepositoryImpl(appContext)
    val locationProvider = LocationProvider(appContext)

    private val userAgentProvider = UserAgentProvider()
    private val okHttpClient = NetworkModule.okHttpClient(userAgentProvider)
    private val nwsApi: NwsApi = NetworkModule.nwsApi(okHttpClient)

    val weatherRepository: WeatherRepository = WeatherRepositoryImpl(
        api = nwsApi,
        pointDao = database.pointMetadataDao(),
        forecastDao = database.forecastCacheDao(),
    )

    val savedLocationRepository = SavedLocationRepository(database.savedLocationDao())

    init {
        // Keep the live User-Agent in lock-step with the saved setting so the
        // header swaps as soon as the user edits it in Settings.
        appScope.launch {
            settingsRepository.userAgent.collectLatest { userAgentProvider.update(it) }
        }
    }

    /** One factory that knows how to build every ViewModel from this container. */
    val viewModelFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer { DashboardViewModel(weatherRepository, locationProvider, settingsRepository) }
        initializer { LocationsViewModel(savedLocationRepository) }
        initializer { SettingsViewModel(settingsRepository) }
    }
}
