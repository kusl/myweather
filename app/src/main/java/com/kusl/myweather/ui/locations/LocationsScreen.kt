package com.kusl.myweather.ui.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusl.myweather.R
import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.domain.model.SavedLocation
import com.kusl.myweather.ui.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    viewModel: LocationsViewModel,
    onShowOnDashboard: (SavedLocation) -> Unit,
) {
    val locations by viewModel.locations.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth()) {
        TopAppBar(
            title = { Text(stringResource(R.string.locations_title)) },
            actions = {
                IconButton(onClick = { showAddDialog = true }) {
                    Icon(AppIcons.Add, contentDescription = stringResource(R.string.add_location))
                }
            },
        )

        if (locations.isEmpty()) {
            Text(
                "No saved locations yet. Tap + to add one you want to monitor.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp),
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(locations, key = { it.id }) { loc ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onShowOnDashboard(loc) },
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(loc.label, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    "%.4f, %.4f".format(loc.latitude, loc.longitude),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            IconButton(onClick = { viewModel.delete(loc.id) }) {
                                Icon(AppIcons.Delete, contentDescription = "Delete ${loc.label}")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddLocationDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { label, lat, lon ->
                viewModel.add(label, lat, lon)
                showAddDialog = false
            },
        )
    }
}

@Composable
private fun AddLocationDialog(
    onDismiss: () -> Unit,
    onConfirm: (label: String, lat: Double, lon: Double) -> Unit,
) {
    var label by remember { mutableStateOf("") }
    var latText by remember { mutableStateOf("") }
    var lonText by remember { mutableStateOf("") }

    val lat = latText.trim().toDoubleOrNull()
    val lon = lonText.trim().toDoubleOrNull()
    val valid = lat != null && lon != null && GeoPoint.isValid(lat, lon)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_location)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text(stringResource(R.string.location_label_hint)) },
                    singleLine = true,
                )
                OutlinedTextField(
                    value = latText,
                    onValueChange = { latText = it },
                    label = { Text(stringResource(R.string.latitude_hint)) },
                    singleLine = true,
                )
                OutlinedTextField(
                    value = lonText,
                    onValueChange = { lonText = it },
                    label = { Text(stringResource(R.string.longitude_hint)) },
                    singleLine = true,
                )
                if (!valid && (latText.isNotBlank() || lonText.isNotBlank())) {
                    Text(
                        "Enter a valid latitude (-90..90) and longitude (-180..180).",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = valid,
                onClick = { if (lat != null && lon != null) onConfirm(label, lat, lon) },
            ) { Text(stringResource(R.string.save)) }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
    )
}
