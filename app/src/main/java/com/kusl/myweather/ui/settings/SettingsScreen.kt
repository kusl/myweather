package com.kusl.myweather.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusl.myweather.R
import com.kusl.myweather.core.Telemetry
import com.kusl.myweather.data.settings.SettingsRepository
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val savedUserAgent by viewModel.userAgent.collectAsStateWithLifecycle()
    val radius by viewModel.neighborhoodRadius.collectAsStateWithLifecycle()

    var draftUserAgent by remember { mutableStateOf(savedUserAgent) }
    // Keep the editable field in sync if the persisted value changes underneath us.
    LaunchedEffect(savedUserAgent) { draftUserAgent = savedUserAgent }

    Column(Modifier.fillMaxWidth()) {
        TopAppBar(title = { Text(stringResource(R.string.settings_title)) })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.user_agent_label), style = MaterialTheme.typography.titleMedium)
                Text(
                    stringResource(R.string.user_agent_help),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                OutlinedTextField(
                    value = draftUserAgent,
                    onValueChange = { draftUserAgent = it },
                    label = { Text(stringResource(R.string.user_agent_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                )
                Button(
                    onClick = { viewModel.setUserAgent(draftUserAgent) },
                    enabled = draftUserAgent.isNotBlank() && draftUserAgent != savedUserAgent,
                ) { Text(stringResource(R.string.save)) }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.neighborhood_radius_label), style = MaterialTheme.typography.titleMedium)
                Text(
                    "How many rings of surrounding grid cells to fetch for the neighborhood matrix. " +
                        "A radius of 1 is a 3\u00D73 grid; 2 is 5\u00D75; 3 is 7\u00D77.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (r in SettingsRepository.MIN_RADIUS..SettingsRepository.MAX_RADIUS) {
                        FilterChip(
                            selected = radius == r,
                            onClick = { viewModel.setNeighborhoodRadius(r) },
                            label = { Text("$r  (${2 * r + 1}\u00D7${2 * r + 1})") },
                        )
                    }
                }
            }

            DiagnosticsSection()

            Text(
                "MyWeather sends no analytics and uses no Google services. " +
                    "Data comes directly from the U.S. National Weather Service API.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

/**
 * A read-only view of the in-memory [Telemetry] breadcrumb log. It exists so the
 * user (or a developer) can see what the app just did — handy for diagnosing a
 * slow location fix or a failed request. This data is local-only: it is never
 * uploaded or persisted.
 */
@Composable
private fun DiagnosticsSection() {
    val logs by Telemetry.entries.collectAsStateWithLifecycle()
    val timeFormatter = remember {
        DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault())
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Diagnostics", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { Telemetry.clear() }, enabled = logs.isNotEmpty()) { Text("Clear") }
        }
        Text(
            "A local-only activity log to help debug issues like a slow location fix. " +
                "Nothing here is uploaded or sent anywhere.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        if (logs.isEmpty()) {
            Text(
                "No events yet.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    // Newest first, capped so the section stays a reasonable size.
                    logs.asReversed().take(40).forEach { entry ->
                        Text(
                            text = "${timeFormatter.format(Instant.ofEpochMilli(entry.epochMs))} " +
                                "${entry.level.name.first()} [${entry.tag}] ${entry.message}",
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}
