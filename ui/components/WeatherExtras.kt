package com.kusl.myweather.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kusl.myweather.domain.model.CurrentObservation
import com.kusl.myweather.domain.model.ForecastPeriod
import com.kusl.myweather.domain.model.LocationSources
import com.kusl.myweather.ui.AppIcons
import java.util.Locale
import kotlin.math.roundToInt

/** A horizontally-scrolling strip of the next several hours; tap a card for detail. */
@Composable
fun HourlyStrip(
    periods: List<ForecastPeriod>,
    onPeriodSelected: (ForecastPeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (periods.isEmpty()) return
    Column(modifier) {
        Text(
            "Hourly",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(periods, key = { it.number }) { period ->
                OutlinedCard(
                    modifier = Modifier
                        .width(96.dp)
                        .clickable { onPeriodSelected(period) },
                ) {
                    Column(Modifier.padding(10.dp)) {
                        Text(hourLabel(period), style = MaterialTheme.typography.labelMedium, maxLines = 1)
                        Text(
                            "${period.temperature}\u00B0${period.temperatureUnit}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(period.shortForecast, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                        period.probabilityOfPrecipitation?.takeIf { it > 0 }?.let {
                            Text("$it%", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}

/** "HH:MM" pulled from the ISO start time, falling back to the period number. */
private fun hourLabel(period: ForecastPeriod): String =
    if (period.startTime.length >= 16) period.startTime.drop(11).take(5) else "#${period.number}"

/** The latest measured conditions from the nearest reporting station. */
@Composable
fun ObservationCard(
    observation: CurrentObservation,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(
                "Current conditions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            val subtitle = listOfNotNull(
                observation.stationId?.let { "Station $it" },
                observation.timestamp,
            ).joinToString(" \u00B7 ")
            if (subtitle.isNotBlank()) {
                Text(
                    subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            observation.textDescription?.takeIf { it.isNotBlank() }?.let {
                Text(it, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 8.dp))
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            observation.temperatureF?.let { StatRow("Temperature", "${fmt1(it)}\u00B0F") }
            observation.relativeHumidityPct?.let { StatRow("Humidity", "$it%") }
            observation.windSpeedMph?.let { speed ->
                val dir = observation.windDirectionDeg?.let { " ${degToCardinal(it)}" }.orEmpty()
                val gust = observation.windGustMph?.let { " (gust ${fmt1(it)} mph)" }.orEmpty()
                StatRow("Wind", "${fmt1(speed)} mph$dir$gust")
            }
            observation.dewpointF?.let { StatRow("Dew point", "${fmt1(it)}\u00B0F") }
            observation.pressureInHg?.let { StatRow("Pressure", "${fmt2(it)} inHg") }
            observation.visibilityMiles?.let { StatRow("Visibility", "${fmt1(it)} mi") }
        }
    }
}

/**
 * Read-only, collapsed-by-default list of the official NWS sources behind this
 * location's data — so a curious user can see (and follow) exactly which office,
 * radar, zones, and endpoints back what they're looking at.
 */
@Composable
fun LocationSourcesCard(
    sources: LocationSources,
    modifier: Modifier = Modifier,
) {
    val rows = buildList {
        sources.forecastOffice?.let { add("Forecast office" to it) }
        sources.radarStation?.let { add("Radar station" to it) }
        sources.forecastZone?.let { add("Forecast zone" to it) }
        sources.county?.let { add("County zone" to it) }
        sources.fireWeatherZone?.let { add("Fire weather zone" to it) }
        sources.forecastUrl?.let { add("Forecast" to it) }
        sources.hourlyForecastUrl?.let { add("Hourly forecast" to it) }
        sources.gridDataUrl?.let { add("Grid data" to it) }
        sources.observationStationsUrl?.let { add("Observation stations" to it) }
    }
    if (rows.isEmpty()) return

    var expanded by remember { mutableStateOf(false) }
    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                AppIcons.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text("Data sources", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (expanded) AppIcons.ExpandLess else AppIcons.ExpandMore,
                contentDescription = if (expanded) "Collapse" else "Expand",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        if (expanded) {
            Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                rows.forEach { (label, value) ->
                    Text(
                        label,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                    Text(value, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}

private val CARDINALS = arrayOf(
    "N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE",
    "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW",
)

/** Map a wind bearing in degrees to a 16-point compass abbreviation. */
private fun degToCardinal(deg: Int): String {
    val normalized = ((deg % 360) + 360) % 360
    return CARDINALS[(normalized / 22.5).roundToInt() % 16]
}

private fun fmt1(value: Double): String = String.format(Locale.US, "%.1f", value)
private fun fmt2(value: Double): String = String.format(Locale.US, "%.2f", value)
