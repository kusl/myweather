package com.kusl.myweather.data.remote

import kotlinx.serialization.json.Json

/**
 * Shared JSON configuration. Tolerant on read (NWS payloads are large and
 * evolving) and used both by the Retrofit converter and for (de)serialising the
 * forecast blobs we cache in Room.
 */
val WeatherJson: Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    isLenient = true
    explicitNulls = false
}
