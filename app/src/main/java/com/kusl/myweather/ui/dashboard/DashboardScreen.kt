package com.kusl.myweather.ui.dashboard

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusl.myweather.R
import com.kusl.myweather.domain.model.ForecastPeriod
import com.kusl.myweather.ui.AppIcons
import com.kusl.myweather.ui.components.AlertsBanner
import com.kusl.myweather.ui.components.ForecastCard
import com.kusl.myweather.ui.components.HourlyStrip
import com.kusl.myweather.ui.components.LocationSourcesCard
import com.kusl.myweather.ui.components.ObservationCard
import com.kusl.myweather.ui.components.PeriodDetailSheet
import com.kusl.myweather.ui.components.TileGrid
import com.kusl.myweather.ui.components.UpcomingPeriods
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    presetLat: Double?,
    presetLon: Double?,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var manualText by rememberSaveable { mutableStateOf("") }
    // Which period (if any) is showing its detail sheet. Transient by design:
    // it's an ephemeral overlay, so we don't preserve it across config changes.
    var selectedPeriod by remember { mutableStateOf<ForecastPeriod?>(null) }
    val context = LocalContext.current

    // When navigated here with explicit coords (e.g. from a saved location), load them.
    LaunchedEffect(presetLat, presetLon) {
        if (presetLat != null && presetLon != null) {
            viewModel.load(com.kusl.myweather.core.GeoPoint(presetLat, presetLon))
        }
    }

    // Surface transient one-shot signals (work started / succeeded / failed) as toasts.
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is DashboardEvent.Message -> Toast.makeText(context, event.text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { grants ->
        val granted = grants.values.any { it }
        if (granted) viewModel.requestDeviceLocation() else viewModel.onPermissionDenied()
    }

    Column(Modifier.fillMaxWidth()) {
        TopAppBar(
            title = { Text(stringResource(R.string.dashboard_title)) },
            actions = {
                IconButton(onClick = { viewModel.refresh() }, enabled = state.hasData && !state.isLoading) {
                    Icon(AppIcons.Refresh, contentDescription = stringResource(R.string.refresh))
                }
            },
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Life-and-safety first: any active watches/warnings sit above everything.
            state.area?.alerts?.takeIf { it.isNotEmpty() }?.let { alerts ->
                item(key = "alerts") { AlertsBanner(alerts) }
            }

            item {
                Button(
                    onClick = {
                        if (viewModel.hasLocationPermission()) {
                            viewModel.requestDeviceLocation()
                        } else {
                            permissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.locationStatus != LocationStatus.Requesting && !state.isLoading,
                ) {
                    Icon(AppIcons.LocationOn, contentDescription = null)
                    Text(
                        stringResource(R.string.use_my_location),
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        value = manualText,
                        onValueChange = { manualText = it },
                        label = { Text("Latitude, longitude") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                    )
                    OutlinedButton(onClick = { viewModel.loadManual(manualText) }) { Text("Go") }
                }
            }

            // Progress: getting a device fix can take a while on a cold start, so
            // show feedback immediately rather than leaving the screen looking idle.
            if (state.locationStatus == LocationStatus.Requesting) {
                item { LoadingRow("Finding your location\u2026") }
            }

            // Status / banners
            when (state.locationStatus) {
                LocationStatus.PermissionDenied -> item {
                    InfoBanner("Location permission was declined. Enter coordinates above to see a forecast.")
                }
                LocationStatus.Unavailable -> item {
                    InfoBanner("Couldn't get a location fix. Make sure location is on, or enter coordinates above.")
                }
                else -> Unit
            }

            if (state.offline) {
                item { WarningBanner(stringResource(R.string.stale_banner)) }
            }

            if (state.isLoading) {
                item { LoadingRow("Loading forecast\u2026") }
            }

            state.message?.let { msg ->
                item { InfoBanner(msg) }
            }

            val area = state.area
            if (area != null) {
                item {
                    ForecastCard(
                        metadata = area.metadata,
                        forecast = area.primary,
                        onPeriodSelected = { selectedPeriod = it },
                    )
                }
                area.observation?.let { obs ->
                    item { ObservationCard(observation = obs) }
                }
                item {
                    TileGrid(
                        tiles = area.tiles,
                        onTileSelected = { viewModel.recenterOnGrid(it.grid) },
                    )
                }
                item {
                    UpcomingPeriods(
                        periods = area.primary.periods,
                        onPeriodSelected = { selectedPeriod = it },
                    )
                }
                if (area.hourly.isNotEmpty()) {
                    item {
                        HourlyStrip(
                            periods = area.hourly,
                            onPeriodSelected = { selectedPeriod = it },
                        )
                    }
                }
                area.sources?.let { sources ->
                    item { LocationSourcesCard(sources = sources) }
                }
            } else if (!state.isLoading &&
                state.message == null &&
                state.locationStatus != LocationStatus.Requesting
            ) {
                item {
                    Text(
                        "Use your location or enter coordinates to load a U.S. forecast.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }

            item { Column(Modifier.padding(bottom = 8.dp)) {} }
        }

        // Detail overlay for a tapped forecast period. Rendered outside the list
        // so it floats above the whole screen.
        selectedPeriod?.let { period ->
            PeriodDetailSheet(period = period, onDismiss = { selectedPeriod = null })
        }
    }
}

@Composable
private fun LoadingRow(text: String) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularProgressIndicator()
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun InfoBanner(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
private fun WarningBanner(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.errorContainer,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                AppIcons.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer,
            )
            Text(
                text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
        }
    }
}
