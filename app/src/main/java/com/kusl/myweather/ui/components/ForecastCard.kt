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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kusl.myweather.domain.model.Forecast
import com.kusl.myweather.domain.model.ForecastPeriod
import com.kusl.myweather.domain.model.PointMetadata

/** Headline "current conditions" card for the user's own cell. Tapping it opens
 * the full detail for the current period. */
@Composable
fun ForecastCard(
    metadata: PointMetadata,
    forecast: Forecast,
    onPeriodSelected: (ForecastPeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    val current = forecast.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = current != null) { current?.let(onPeriodSelected) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = metadata.displayName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Text(
                text = "Grid ${metadata.grid}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            if (current != null) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${current.temperature}\u00B0${current.temperatureUnit}",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = current.shortForecast,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        current.probabilityOfPrecipitation?.let {
                            Text(
                                "Precip $it%",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                        Text(
                            "Wind ${current.windSpeed} ${current.windDirection}".trim(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
                Text(
                    text = current.detailedForecast,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        }
    }
}

/** A horizontally-scrolling strip of the upcoming forecast periods; tap one for detail. */
@Composable
fun UpcomingPeriods(
    periods: List<ForecastPeriod>,
    onPeriodSelected: (ForecastPeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (periods.isEmpty()) return
    Column(modifier) {
        Text(
            "Upcoming",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(periods, key = { it.number }) { period ->
                OutlinedCard(
                    modifier = Modifier
                        .width(140.dp)
                        .clickable { onPeriodSelected(period) },
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(period.name, style = MaterialTheme.typography.labelLarge, maxLines = 1)
                        Text(
                            "${period.temperature}\u00B0${period.temperatureUnit}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(period.shortForecast, style = MaterialTheme.typography.bodySmall, maxLines = 3)
                        period.probabilityOfPrecipitation?.let {
                            Text("Precip $it%", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}
