package com.kusl.myweather

import android.app.Application
import com.kusl.myweather.core.Telemetry
import com.kusl.myweather.di.AppContainer

/**
 * Application entry point. Owns the manual DI [AppContainer] for the process
 * lifetime.
 *
 * The app remains fully local and serverless: no analytics, no crash reporting,
 * and no third-party initialisers run here. The only "telemetry" is
 * [Telemetry] — an in-memory breadcrumb log mirrored to Logcat that never
 * leaves the device (see its KDoc).
 */
class MyWeatherApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        Telemetry.i("App", "MyWeather started")
    }
}
