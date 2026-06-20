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

// ── /gridpoints/.../forecast ───────────────────────────────────────────────────

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
