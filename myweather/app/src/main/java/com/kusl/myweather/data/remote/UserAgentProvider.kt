package com.kusl.myweather.data.remote

import java.util.concurrent.atomic.AtomicReference

/**
 * Thread-safe holder for the current NWS User-Agent. The OkHttp interceptor
 * reads it synchronously on every request; the settings layer pushes updates
 * into it whenever the user changes the value, so the header swaps live without
 * rebuilding the HTTP client.
 */
class UserAgentProvider(initial: String = DEFAULT) {
    private val ref = AtomicReference(initial.ifBlank { DEFAULT })

    fun current(): String = ref.get()

    fun update(value: String) {
        ref.set(value.ifBlank { DEFAULT })
    }

    companion object {
        /**
         * NWS asks every client to identify itself and provide a contact. This
         * default is deliberately editable in Settings — users are encouraged to
         * add their own contact so NWS can reach them before rate-limiting.
         */
        const val DEFAULT = "MyWeather/1.0 (github.com/kusl/myweather; set-your-contact@example.com)"
    }
}
