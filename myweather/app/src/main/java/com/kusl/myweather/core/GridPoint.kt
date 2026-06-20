package com.kusl.myweather.core

/**
 * A National Weather Service forecast grid cell, identified by the forecast
 * office (`gridId` / `cwa`, e.g. "AKQ") and the X/Y indices within it.
 */
data class GridPoint(val gridId: String, val gridX: Int, val gridY: Int) {
    override fun toString(): String = "$gridId/$gridX,$gridY"
}
