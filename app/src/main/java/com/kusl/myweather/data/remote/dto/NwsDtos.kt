package com.kusl.myweather.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
 * Wire models for the NWS GeoJSON responses. Every field is nullable and unknown
 * keys are ignored (see WeatherJson), so partial or evolving payloads never crash
 * deserialization — the mappers decide what's required.
 *
 * Shapes verified against the live samples for:
 *   GET /points/{lat},{lon}
 *   GET /gridpoints/{office}/{x},{y}/forecast
 *   GET /gridpoints/{office}/{x},{y}/forecast/hourly   (same shape as /forecast)
 *   GET /gridpoints/{office}/{x},{y}/stations
 *   GET /stations/{id}/observations/latest
 *   GET /alerts/active?point={lat},{lon}
 */

// ── /points ──────────────────────────────────────────────────────────────────

@Serializable
data class PointResponseDto(
    val properties: PointPropertiesDto? = null,
)

@Serializable
data class PointPropertiesDto(
    @SerialName("gridId") val gridId: String? = null,
    @SerialName("gridX") val gridX: Int? = null,
    @SerialName("gridY") val gridY: Int? = null,
    val forecast: String? = null,
    val forecastHourly: String? = null,
    val forecastGridData: String? = null,
    val observationStations: String? = null,
    val forecastOffice: String? = null,
    val forecastZone: String? = null,
    val county: String? = null,
    val fireWeatherZone: String? = null,
    val radarStation: String? = null,
    val timeZone: String? = null,
    val relativeLocation: RelativeLocationDto? = null,
)

@Serializable
data class RelativeLocationDto(
    val properties: RelativeLocationPropertiesDto? = null,
)

@Serializable
data class RelativeLocationPropertiesDto(
    val city: String? = null,
    val state: String? = null,
)

// ── /gridpoints/.../forecast (and /forecast/hourly) ────────────────────────────

@Serializable
data class ForecastResponseDto(
    val properties: ForecastPropertiesDto? = null,
)

@Serializable
data class ForecastPropertiesDto(
    val generatedAt: String? = null,
    val updateTime: String? = null,
    val periods: List<PeriodDto>? = null,
)

@Serializable
data class PeriodDto(
    val number: Int = 0,
    val name: String? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val isDaytime: Boolean = true,
    val temperature: Int = 0,
    val temperatureUnit: String? = null,
    val probabilityOfPrecipitation: QuantitativeValueDto? = null,
    val windSpeed: String? = null,
    val windDirection: String? = null,
    val icon: String? = null,
    val shortForecast: String? = null,
    val detailedForecast: String? = null,
)

@Serializable
data class QuantitativeValueDto(
    val unitCode: String? = null,
    val value: Double? = null,
)

// ── /alerts/active ─────────────────────────────────────────────────────────────

@Serializable
data class AlertsResponseDto(
    val features: List<AlertFeatureDto>? = null,
)

@Serializable
data class AlertFeatureDto(
    val id: String? = null,
    val properties: AlertPropertiesDto? = null,
)

@Serializable
data class AlertPropertiesDto(
    val id: String? = null,
    val areaDesc: String? = null,
    val sent: String? = null,
    val effective: String? = null,
    val onset: String? = null,
    val expires: String? = null,
    val ends: String? = null,
    val status: String? = null,
    val messageType: String? = null,
    val category: String? = null,
    val severity: String? = null,
    val certainty: String? = null,
    val urgency: String? = null,
    val event: String? = null,
    val senderName: String? = null,
    val headline: String? = null,
    val description: String? = null,
    val instruction: String? = null,
    val response: String? = null,
)

// ── /gridpoints/.../stations ───────────────────────────────────────────────────

@Serializable
data class StationsResponseDto(
    val features: List<StationFeatureDto>? = null,
)

@Serializable
data class StationFeatureDto(
    val id: String? = null,
    val properties: StationPropertiesDto? = null,
)

@Serializable
data class StationPropertiesDto(
    val stationIdentifier: String? = null,
    val name: String? = null,
)

// ── /stations/{id}/observations/latest ─────────────────────────────────────────

@Serializable
data class ObservationResponseDto(
    val properties: ObservationPropertiesDto? = null,
)

@Serializable
data class ObservationPropertiesDto(
    val timestamp: String? = null,
    val textDescription: String? = null,
    val rawMessage: String? = null,
    val temperature: QuantitativeValueDto? = null,
    val dewpoint: QuantitativeValueDto? = null,
    val windDirection: QuantitativeValueDto? = null,
    val windSpeed: QuantitativeValueDto? = null,
    val windGust: QuantitativeValueDto? = null,
    val barometricPressure: QuantitativeValueDto? = null,
    val seaLevelPressure: QuantitativeValueDto? = null,
    val visibility: QuantitativeValueDto? = null,
    val relativeHumidity: QuantitativeValueDto? = null,
    val windChill: QuantitativeValueDto? = null,
    val heatIndex: QuantitativeValueDto? = null,
)
