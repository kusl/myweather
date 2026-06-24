package com.kusl.myweather.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kusl.myweather.domain.model.ForecastPeriod

/**
 * A bottom sheet showing every detail NWS gives us for one forecast period,
 * opened by tapping the headline card or any "upcoming" period. Read-only — it's
 * purely a richer view of data we already hold, so no network call is involved.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodDetailSheet(
    period: ForecastPeriod,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp),
        ) {
            Text(
                text = period.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "${period.temperature}\u00B0${period.temperatureUnit}",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.SemiBold,
            )
            period.shortForecast.takeIf { it.isNotBlank() }?.let {
                Text(it, style = MaterialTheme.typography.titleMedium)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            DetailRow("Time of day", if (period.isDaytime) "Daytime" else "Overnight")
            period.probabilityOfPrecipitation?.let { DetailRow("Chance of precipitation", "$it%") }
            DetailRow(
                label = "Wind",
                value = listOf(period.windSpeed, period.windDirection)
                    .filter { it.isNotBlank() }
                    .joinToString(" "),
            )
            DetailRow("Starts", period.startTime)
            DetailRow("Ends", period.endTime)

            period.detailedForecast.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = "Detailed forecast",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
                )
                Text(it, style = MaterialTheme.typography.bodyMedium)
            }

            // The icon is a URL; show it muted for the curious rather than hide it.
            period.icon.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    if (value.isBlank()) return
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}
