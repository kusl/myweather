package com.kusl.myweather.data.remote

import com.kusl.myweather.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Hand-wired networking stack (no DI framework). Builds a single OkHttp client
 * and Retrofit instance for the app's lifetime.
 */
object NetworkModule {

    const val BASE_URL = "https://api.weather.gov/"

    fun okHttpClient(userAgentProvider: UserAgentProvider): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(NwsHeaderInterceptor(userAgentProvider))
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC },
            )
        }
        return builder.build()
    }

    fun nwsApi(client: OkHttpClient): NwsApi {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(WeatherJson.asConverterFactory(contentType))
            .build()
            .create(NwsApi::class.java)
    }
}
