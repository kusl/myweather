package com.kusl.myweather.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * [Telemetry] is a process-wide singleton, so each test starts from a clean
 * buffer. The Logcat calls inside [Telemetry] are inert here because the module
 * sets `testOptions.unitTests.isReturnDefaultValues = true`.
 *
 * Timestamps are rendered in the device/JVM local zone, so the assertions check
 * structure and content (ordering, level letter, tag, message) rather than the
 * exact time text, keeping the test independent of the runner's time zone.
 */
class TelemetryTest {

    @Before
    fun setUp() = Telemetry.clear()

    @Test
    fun `exportText is empty when there are no events`() {
        assertEquals("", Telemetry.exportText())
    }

    @Test
    fun `exportText renders one line per event, oldest first`() {
        Telemetry.i("Location", "requesting fix")
        Telemetry.w("Net", "slow response")
        Telemetry.e("Net", "boom")

        val lines = Telemetry.exportText().lines()
        assertEquals(3, lines.size)
        assertTrue(lines[0].contains("I [Location] requesting fix"))
        assertTrue(lines[1].contains("W [Net] slow response"))
        assertTrue(lines[2].contains("E [Net] boom"))
    }

    @Test
    fun `clear empties the export`() {
        Telemetry.i("Location", "requesting fix")
        assertTrue(Telemetry.exportText().isNotEmpty())

        Telemetry.clear()
        assertEquals("", Telemetry.exportText())
    }
}
