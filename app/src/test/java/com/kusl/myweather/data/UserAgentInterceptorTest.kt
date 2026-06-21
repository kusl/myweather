package com.kusl.myweather.data

import com.kusl.myweather.data.remote.NwsHeaderInterceptor
import com.kusl.myweather.data.remote.UserAgentProvider
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Verifies [NwsHeaderInterceptor] against a *real* OkHttp interceptor chain.
 *
 * We deliberately do NOT hand-implement [okhttp3.Interceptor.Chain]: that
 * interface gains members between OkHttp releases (OkHttp 5 added `dns`,
 * `socketFactory`, `authenticator`, `followRedirects`, … to `Chain`), so a
 * hand-rolled fake stops compiling on every upgrade. Instead we run the
 * interceptor inside a real [OkHttpClient] and short-circuit the call with a
 * terminal interceptor that captures the outgoing request and returns a canned
 * 200 *without calling `chain.proceed()`*. Because the short-circuit happens in
 * the application-interceptor layer, OkHttp never opens a socket — no DNS, no
 * network — so this stays a fast, hermetic pure-JVM unit test.
 */
class UserAgentInterceptorTest {

    /** Drives a single request through [interceptor] and returns the request OkHttp produced. */
    private fun requestThrough(interceptor: NwsHeaderInterceptor): Request {
        lateinit var captured: Request
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor) // the unit under test
            .addInterceptor { chain -> // terminal: capture the request, return a canned 200, no network
                captured = chain.request()
                Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .message("OK")
                    .body("{}".toResponseBody(null))
                    .build()
            }
            .build()

        client.newCall(Request.Builder().url("https://api.weather.gov/points/1,2").build())
            .execute()
            .close()
        return captured
    }

    @Test
    fun `sets user agent from provider and geo+json accept`() {
        val request = requestThrough(
            NwsHeaderInterceptor(UserAgentProvider("MyWeather/test (contact@example.com)")),
        )
        assertEquals("MyWeather/test (contact@example.com)", request.header("User-Agent"))
        assertEquals("application/geo+json", request.header("Accept"))
    }

    @Test
    fun `picks up an updated user agent on the next request`() {
        val provider = UserAgentProvider("first/1.0")
        val interceptor = NwsHeaderInterceptor(provider)

        assertEquals("first/1.0", requestThrough(interceptor).header("User-Agent"))

        provider.update("second/2.0 (new@example.com)")
        assertEquals("second/2.0 (new@example.com)", requestThrough(interceptor).header("User-Agent"))
    }

    @Test
    fun `blank user agent falls back to the default`() {
        val request = requestThrough(NwsHeaderInterceptor(UserAgentProvider("")))
        assertEquals(UserAgentProvider.DEFAULT, request.header("User-Agent"))
    }
}
