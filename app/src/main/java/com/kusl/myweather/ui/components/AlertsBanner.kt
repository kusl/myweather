package com.kusl.myweather.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kusl.myweather.domain.model.WeatherAlert
import com.kusl.myweather.ui.AppIcons

/**
 * The active watches/warnings/advisories for the current location, shown at the
 * very top of the dashboard because this is life-and-safety information.
 *
 * Alerts arrive already sorted most-severe-first (see NwsMappers.toAlerts). The
 * most severe one starts expanded; the rest start collapsed but can be opened.
 * The human-authored [WeatherAlert.description] and [WeatherAlert.instruction]
 * are rendered **verbatim** — never trimmed, summarised, or reflowed.
 */
@Composable
fun AlertsBanner(
    alerts: List<WeatherAlert>,
    modifier: Modifier = Modifier,
) {
    if (alerts.isEmpty()) return

    // Per-alert expansion, keyed by stable id. Absent => fall back to the
    // default (top/most-severe alert open, the rest closed). Re-keyed on the
    // list itself so a refresh with new alerts resets to that default.
    val expansion = remember(alerts) { mutableStateMapOf<String, Boolean>() }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = if (alerts.size == 1) "Active alert" else "Active alerts (${alerts.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
        )
        alerts.forEachIndexed { index, alert ->
            val expanded = expansion[alert.id] ?: (index == 0)
            AlertCard(
                alert = alert,
                expanded = expanded,
                onToggle = { expansion[alert.id] = !expanded },
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }
    }
}

@Composable
private fun AlertCard(
    alert: WeatherAlert,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    // Colour by severity so the most dangerous alerts read as alarming.
    val (container, content) = when (WeatherAlert.severityRank(alert.severity)) {
        0, 1 -> scheme.errorContainer to scheme.onErrorContainer        // Extreme / Severe
        2 -> scheme.tertiaryContainer to scheme.onTertiaryContainer      // Moderate
        else -> scheme.secondaryContainer to scheme.onSecondaryContainer // Minor / Unknown
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = container, contentColor = content),
    ) {
        // Header: always visible; tap anywhere on it to expand/collapse.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(AppIcons.Warning, contentDescription = null, tint = content)
            Column(Modifier.weight(1f)) {
                Text(
                    text = alert.event,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = content,
                )
                alert.headline?.takeIf { it.isNotBlank() }?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium, color = content)
                }
            }
            Icon(
                imageVector = if (expanded) AppIcons.ExpandLess else AppIcons.ExpandMore,
                contentDescription = if (expanded) "Collapse" else "Expand",
                tint = content,
            )
        }

        // Where/when stays visible even when collapsed, so the gist is always there.
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            alert.areaDesc?.takeIf { it.isNotBlank() }?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium, color = content)
            }
            alert.expires?.takeIf { it.isNotBlank() }?.let {
                Text("Expires $it", style = MaterialTheme.typography.labelMedium, color = content)
            }

            if (expanded) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = content.copy(alpha = 0.3f),
                )
                // Verbatim NWS narrative — the "what / where / when / impacts".
                Text(text = alert.description, style = MaterialTheme.typography.bodyMedium, color = content)
                alert.instruction?.takeIf { it.isNotBlank() }?.let {
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = content,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                    Text(text = it, style = MaterialTheme.typography.bodyMedium, color = content)
                }
                Text(
                    text = "Severity ${alert.severity} \u00B7 Urgency ${alert.urgency} \u00B7 " +
                        "Certainty ${alert.certainty}",
                    style = MaterialTheme.typography.labelMedium,
                    color = content,
                    modifier = Modifier.padding(top = 12.dp),
                )
                alert.senderName?.takeIf { it.isNotBlank() }?.let {
                    Text(it, style = MaterialTheme.typography.labelMedium, color = content)
                }
            }
        }
    }
}
