package com.kusl.myweather.data

import com.kusl.myweather.data.remote.NwsHeaderInterceptor
import com.kusl.myweather.data.remote.UserAgentProvider
import okhttp3.Call
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test

class UserAgentInterceptorTest {

    @Test
    fun `sets user agent from provider and geo+json accept`() {
        val provider = UserAgentProvider("MyWeather/test (contact@example.com)")
        val chain = RecordingChain()
        NwsHeaderInterceptor(provider).intercept(chain)

        assertEquals("MyWeather/test (contact@example.com)", chain.lastRequest!!.header("User-Agent"))
        assertEquals("application/geo+json", chain.lastRequest!!.header("Accept"))
    }

    @Test
    fun `picks up an updated user agent on the next request`() {
        val provider = UserAgentProvider("first/1.0")
        val interceptor = NwsHeaderInterceptor(provider)

        val c1 = RecordingChain()
        interceptor.intercept(c1)
        assertEquals("first/1.0", c1.lastRequest!!.header("User-Agent"))

        provider.update("second/2.0 (new@example.com)")
        val c2 = RecordingChain()
        interceptor.intercept(c2)
        assertEquals("second/2.0 (new@example.com)", c2.lastRequest!!.header("User-Agent"))
    }

    @Test
    fun `blank user agent falls back to the default`() {
        val provider = UserAgentProvider("")
        val chain = RecordingChain()
        NwsHeaderInterceptor(provider).intercept(chain)
        assertEquals(UserAgentProvider.DEFAULT, chain.lastRequest!!.header("User-Agent"))
    }

    /** Minimal OkHttp Chain that captures the request and returns a canned 200. */
    private class RecordingChain : Interceptor.Chain {
        var lastRequest: Request? = null
        private val original = Request.Builder().url("https://api.weather.gov/points/1,2").build()

        override fun request(): Request = original

        override fun proceed(request: Request): Response {
            lastRequest = request
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body("{}".toResponseBody(null))
                .build()
        }

        override fun connection(): Connection? = null
        override fun call(): Call = throw UnsupportedOperationException()
        override fun connectTimeoutMillis(): Int = 0
        override fun withConnectTimeout(timeout: Int, unit: java.util.concurrent.TimeUnit): Interceptor.Chain = this
        override fun readTimeoutMillis(): Int = 0
        override fun withReadTimeout(timeout: Int, unit: java.util.concurrent.TimeUnit): Interceptor.Chain = this
        override fun writeTimeoutMillis(): Int = 0
        override fun withWriteTimeout(timeout: Int, unit: java.util.concurrent.TimeUnit): Interceptor.Chain = this
    }
}
