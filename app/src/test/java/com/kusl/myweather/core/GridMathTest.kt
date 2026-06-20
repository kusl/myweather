package com.kusl.myweather.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GridMathTest {

    private val origin = GridPoint("AKQ", 50, 60)

    @Test
    fun `surrounding radius 1 returns 8 cells excluding origin`() {
        val cells = GridMath.surrounding(origin, radius = 1)
        assertEquals(8, cells.size)
        assertFalse(cells.contains(origin))
        // All within Chebyshev distance 1.
        assertTrue(cells.all { kotlin.math.abs(it.gridX - 50) <= 1 && kotlin.math.abs(it.gridY - 60) <= 1 })
        // Same office.
        assertTrue(cells.all { it.gridId == "AKQ" })
    }

    @Test
    fun `surrounding radius 2 returns 24 cells`() {
        assertEquals(24, GridMath.surrounding(origin, radius = 2).size)
    }

    @Test
    fun `surrounding drops negative indices near the grid edge`() {
        val edge = GridPoint("AKQ", 0, 0)
        val cells = GridMath.surrounding(edge, radius = 1)
        // Only (0,1),(1,0),(1,1) are valid.
        assertEquals(3, cells.size)
        assertTrue(cells.all { it.gridX >= 0 && it.gridY >= 0 })
    }

    @Test
    fun `block with origin is square and north-up with origin centered`() {
        val block = GridMath.blockWithOrigin(origin, radius = 1)
        assertEquals(3, block.size)
        assertTrue(block.all { it.size == 3 })
        // Center cell is the origin.
        assertEquals(origin, block[1][1])
        // Top row is the northern (higher Y) row.
        assertEquals(61, block[0][1]?.gridY)
        assertEquals(59, block[2][1]?.gridY)
    }

    @Test
    fun `block represents off-grid cells as null`() {
        val block = GridMath.blockWithOrigin(GridPoint("AKQ", 0, 0), radius = 1)
        // Bottom row and left column contain negatives => nulls.
        assertNull(block[2][0]) // (x=-1, y=-1)
        assertNull(block[1][0]) // (x=-1, y=0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `radius must be positive`() {
        GridMath.surrounding(origin, radius = 0)
    }
}
