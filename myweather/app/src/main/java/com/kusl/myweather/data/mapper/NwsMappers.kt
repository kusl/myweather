package com.kusl.myweather.data.mapper

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.core.GridPoint
import com.kusl.myweather.data.remote.dto.ForecastResponseDto
import com.kusl.myweather.data.remote.dto.PeriodDto
import com.kusl.myweather.data.remote.dto.PointResponseDto
import com.kusl.myweather.domain.model.Forecast
import com.kusl.myweather.domain.model.ForecastPeriod
import com.kusl.myweather.domain.model.PointMetadata
import kotlin.math.roundToInt

/**
 * Pure DTO -> domain mapping. No I/O, no Android — fully unit-testable. Returns
 * null when a required field (the grid identity) is missing, mirroring the
 * .NET client's "missing grid metadata" guard.
 */
object NwsMappers {

    fun toPointMetadata(query: GeoPoint, dto: PointResponseDto?): PointMetadata? {
        val props = dto?.properties ?: return null
        val gridId = props.gridId ?: return null
        val gridX = props.gridX ?: return null
        val gridY = props.gridY ?: return null
        val loc = props.relativeLocation?.properties
        return PointMetadata(
            queryLatitude = query.latitude,
            queryLongitude = query.longitude,
            gridId = gridId,
            gridX = gridX,
            gridY = gridY,
            city = loc?.city,
            state = loc?.state,
            timeZone = props.timeZone,
        )
    }

    fun toForecast(grid: GridPoint, dto: ForecastResponseDto?): Forecast? {
        val props = dto?.properties ?: return null
        return Forecast(
            gridId = grid.gridId,
            gridX = grid.gridX,
            gridY = grid.gridY,
            generatedAt = props.generatedAt,
            updateTime = props.updateTime,
            periods = (props.periods ?: emptyList()).map { it.toDomain() },
        )
    }

    private fun PeriodDto.toDomain(): ForecastPeriod = ForecastPeriod(
        number = number,
        name = name.orEmpty(),
        startTime = startTime.orEmpty(),
        endTime = endTime.orEmpty(),
        isDaytime = isDaytime,
        temperature = temperature,
        temperatureUnit = temperatureUnit ?: "F",
        probabilityOfPrecipitation = probabilityOfPrecipitation?.value?.roundToInt(),
        windSpeed = windSpeed.orEmpty(),
        windDirection = windDirection.orEmpty(),
        shortForecast = shortForecast.orEmpty(),
        detailedForecast = detailedForecast.orEmpty(),
        icon = icon.orEmpty(),
    )
}
