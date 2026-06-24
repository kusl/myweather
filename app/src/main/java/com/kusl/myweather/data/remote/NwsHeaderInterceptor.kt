package com.kusl.myweather.data.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Injects the (dynamic) NWS-required headers on every request:
 *  - `User-Agent`: pulled fresh from [UserAgentProvider] so the user-defined
 *    value in Settings takes effect immediately.
 *  - `Accept: application/geo+json`: the GeoJSON representation we parse.
 *
 * Caching is handled one layer out by [HttpCacheInterceptor] (a single,
 * Room-backed transport cache) — not by an OkHttp disk cache — so freshness,
 * de-duplication, and negative caching all live in one explicit place. This
 * interceptor therefore concerns itself only with headers.
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
