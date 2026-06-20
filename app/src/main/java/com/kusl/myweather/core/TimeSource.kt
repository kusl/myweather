package com.kusl.myweather.core

/** Trivial clock seam so cache-freshness logic is deterministic under test. */
fun interface TimeSource {
    fun nowMs(): Long
}

object SystemTimeSource : TimeSource {
    override fun nowMs(): Long = System.currentTimeMillis()
}
