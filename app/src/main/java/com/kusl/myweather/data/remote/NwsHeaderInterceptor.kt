package com.kusl.myweather.data.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Injects the (dynamic) NWS-required headers on every request:
 *  - `User-Agent`: pulled fresh from [UserAgentProvider] so the user-defined
 *    value in Settings takes effect immediately.
 *  - `Accept: application/geo+json`: the GeoJSON representation we parse.
 *
 * We do NOT install an OkHttp disk cache; forecast freshness is managed
 * explicitly in Room (TTL + conditional GET), so a second HTTP cache would only
 * add confusion.
 */
class NwsHeaderInterceptor(
    private val userAgentProvider: UserAgentProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("User-Agent", userAgentProvider.current())
            .header("Accept", "application/geo+json")
            .build()
        return chain.proceed(request)
    }
}
