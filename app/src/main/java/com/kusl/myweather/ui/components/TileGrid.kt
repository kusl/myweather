package com.kusl.myweather.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kusl.myweather.domain.model.WeatherTile

/**
 * Renders the neighborhood as a spatial matrix. Tiles are positioned by their
 * NWS grid X/Y (north-up), so the layout mirrors real geography; the user's own
 * cell is highlighted. Cells with no fetched forecast render as muted blanks so
 * the grid stays aligned.
 *
 * Tapping a neighbouring cell invokes [onTileSelected] — the dashboard uses this
 * to re-centre the matrix on that cell. The primary (centre) cell and empty
 * cells are not interactive.
 */
@Composable
fun TileGrid(
    tiles: List<WeatherTile>,
    modifier: Modifier = Modifier,
    onTileSelected: (WeatherTile) -> Unit = {},
) {
    if (tiles.isEmpty()) return

    val minX = tiles.minOf { it.grid.gridX }
    val maxX = tiles.maxOf { it.grid.gridX }
    val minY = tiles.minOf { it.grid.gridY }
    val maxY = tiles.maxOf { it.grid.gridY }
    val byCoord = tiles.associateBy { it.grid.gridX to it.grid.gridY }

    Column(modifier.fillMaxWidth()) {
        Text(
            "Neighborhood",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
        )
        Text(
            "Tap a surrounding cell to centre on it.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
        )
        // Rows from north (max Y) down to south (min Y).
        for (y in maxY downTo minY) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                for (x in minX..maxX) {
                    val tile = byCoord[x to y]
                    // Only filled, non-primary cells are tappable.
                    val onClick: (() -> Unit)? =
                        tile?.takeIf { it.forecast?.current != null && !it.isPrimary }
                            ?.let { t -> { onTileSelected(t) } }
                    TileCell(tile = tile, onClick = onClick, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun TileCell(
    tile: WeatherTile?,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    val period = tile?.forecast?.current
    val isPrimary = tile?.isPrimary == true

    if (tile == null || period == null) {
        Surface(
            modifier = modifier.aspectRatio(1f),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
            shape = MaterialTheme.shapes.medium,
        ) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    "\u2014",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        return
    }

    val container =
        if (isPrimary) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.secondaryContainer
    val onContainer =
        if (isPrimary) MaterialTheme.colorScheme.onTertiaryContainer
        else MaterialTheme.colorScheme.onSecondaryContainer

    val cellModifier =
        if (onClick != null) {
            modifier.aspectRatio(1f).clickable(onClickLabel = "Center on this cell") { onClick() }
        } else {
            modifier.aspectRatio(1f)
        }

    Card(
        modifier = cellModifier,
        colors = CardDefaults.cardColors(containerColor = container),
        border = if (isPrimary) BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary) else null,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "${period.temperature}\u00B0",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = onContainer,
            )
            Text(
                text = period.shortForecast,
                style = MaterialTheme.typography.labelSmall,
                color = onContainer,
                textAlign = TextAlign.Center,
                maxLines = 2,
            )
            period.probabilityOfPrecipitation?.takeIf { it > 0 }?.let {
                Text(
                    "$it%",
                    style = MaterialTheme.typography.labelSmall,
                    color = onContainer,
                )
            }
        }
    }
}
