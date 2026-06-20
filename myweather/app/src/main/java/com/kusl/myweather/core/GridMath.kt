package com.kusl.myweather.core

/**
 * Pure geometry for the "neighborhood / tile matrix" strategy: given the user's
 * grid cell, compute the surrounding cells whose data is relevant when the user
 * sits near a cell boundary, so we can render a comprehensive local matrix.
 *
 * Ported from the .NET dashboard's `GridNeighborhood`.
 */
object GridMath {

    /**
     * The cells surrounding [origin] within a Chebyshev distance of [radius]
     * (default 1 => the 8 cells of a 3x3 block), excluding the origin itself.
     * Cells with negative indices are dropped because NWS grid coordinates are
     * always non-negative.
     */
    fun surrounding(origin: GridPoint, radius: Int = 1): List<GridPoint> {
        require(radius > 0) { "radius must be positive (was $radius)." }
        val result = ArrayList<GridPoint>(((2 * radius + 1) * (2 * radius + 1)) - 1)
        for (dx in -radius..radius) {
            for (dy in -radius..radius) {
                if (dx == 0 && dy == 0) continue
                val x = origin.gridX + dx
                val y = origin.gridY + dy
                if (x < 0 || y < 0) continue
                result.add(origin.copy(gridX = x, gridY = y))
            }
        }
        return result
    }

    /**
     * The full block including the origin, laid out row-major (top-left to
     * bottom-right) for grid rendering. Negative-index cells are represented as
     * null so the UI can render an empty slot and keep the matrix aligned.
     */
    fun blockWithOrigin(origin: GridPoint, radius: Int = 1): List<List<GridPoint?>> {
        require(radius > 0) { "radius must be positive (was $radius)." }
        val rows = ArrayList<List<GridPoint?>>(2 * radius + 1)
        // dy descending so that "north" (larger Y in many NWS grids) renders on top.
        for (dy in radius downTo -radius) {
            val row = ArrayList<GridPoint?>(2 * radius + 1)
            for (dx in -radius..radius) {
                val x = origin.gridX + dx
                val y = origin.gridY + dy
                row.add(if (x < 0 || y < 0) null else origin.copy(gridX = x, gridY = y))
            }
            rows.add(row)
        }
        return rows
    }
}
