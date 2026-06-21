package com.kusl.myweather.core

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Tiny, dependency-free, in-process telemetry. It does two things and only two:
 *
 *  1. Mirrors every event to Logcat via [android.util.Log] (so `adb logcat -s MyWeather`
 *     shows the app's breadcrumbs during development).
 *  2. Retains the most recent [CAPACITY] events in a ring buffer in memory and
 *     publishes them as a [StateFlow] so they can be shown in-app
 *     (Settings → Diagnostics).
 *
 * Crucially, this is **local-only telemetry**: there is no network sink, no
 * analytics SDK, no Google dependency, and nothing is ever written to disk or
 * sent off the device — in keeping with the project's privacy-first, serverless
 * ethos. It exists purely so the app (and its user) can answer "what just
 * happened?" when something like a slow location fix or a failed request occurs.
 *
 * Thread-safe: the buffer is guarded by a monitor and the public view is an
 * immutable snapshot republished on every change. The Logcat calls are safe in
 * JVM unit tests because the module enables
 * `testOptions.unitTests.isReturnDefaultValues`, so the android.jar stubs no-op
 * instead of throwing.
 */
object Telemetry {

    enum class Level { DEBUG, INFO, WARN, ERROR }

    data class Entry(
        val epochMs: Long,
        val level: Level,
        val tag: String,
        val message: String,
    )

    /** Most recent events first is computed at read sites; storage is oldest-first. */
    private const val CAPACITY = 200
    private const val LOG_TAG = "MyWeather"

    private val lock = Any()
    private val buffer = ArrayDeque<Entry>(CAPACITY)

    private val _entries = MutableStateFlow<List<Entry>>(emptyList())

    /** Oldest-first snapshot of the retained events. */
    val entries: StateFlow<List<Entry>> = _entries.asStateFlow()

    fun d(tag: String, message: String) = record(Level.DEBUG, tag, message, null)
    fun i(tag: String, message: String) = record(Level.INFO, tag, message, null)
    fun w(tag: String, message: String) = record(Level.WARN, tag, message, null)
    fun e(tag: String, message: String, throwable: Throwable? = null) =
        record(Level.ERROR, tag, message, throwable)

    /** Wipe the in-memory buffer (e.g. the Diagnostics "Clear" action). */
    fun clear() {
        synchronized(lock) {
            buffer.clear()
            _entries.value = emptyList()
        }
    }

    private fun record(level: Level, tag: String, message: String, throwable: Throwable?) {
        val line = "[$tag] $message"
        when (level) {
            Level.DEBUG -> Log.d(LOG_TAG, line)
            Level.INFO -> Log.i(LOG_TAG, line)
            Level.WARN -> Log.w(LOG_TAG, line)
            Level.ERROR -> if (throwable != null) Log.e(LOG_TAG, line, throwable) else Log.e(LOG_TAG, line)
        }

        val text = if (throwable != null) "$message — ${throwable.message}" else message
        val entry = Entry(System.currentTimeMillis(), level, tag, text)
        synchronized(lock) {
            while (buffer.size >= CAPACITY) buffer.removeFirst()
            buffer.addLast(entry)
            _entries.value = buffer.toList()
        }
    }
}
