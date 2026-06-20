package com.kusl.myweather

import android.app.Application
import com.kusl.myweather.di.AppContainer

/**
 * Application entry point. Owns the manual DI [AppContainer] for the process
 * lifetime. No analytics, crash reporting, or third-party initialisers run here
 * — the app is fully local and serverless.
 */
class MyWeatherApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
