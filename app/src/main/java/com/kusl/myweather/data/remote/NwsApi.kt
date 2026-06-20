package com.kusl.myweather.data.remote

import com.kusl.myweather.data.remote.dto.ForecastResponseDto
import com.kusl.myweather.data.remote.dto.PointResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Typed view over the public NWS API. Returns Retrofit [Response] (not the bare
 * body) so the repository can read the `ETag` / `Cache-Control` headers and
 * handle `304 Not Modified` — the cheap-refresh path of our cache.
 *
 * Coordinate/grid path segments are passed pre-encoded so the literal commas
 * (`lat,lon`, `x,y`) reach NWS unescaped, exactly as the API expects.
 */
interface NwsApi {

    /** Resolve a coordinate to its forecast grid cell. `point` is `"lat,lon"`. */
    @GET("points/{point}")
    suspend fun getPoint(
        @Path(value = "point", encoded = true) point: String,
    ): Response<PointResponseDto>

    /** The multi-day forecast for a grid cell. `coords` is `"gridX,gridY"`. */
    @GET("gridpoints/{office}/{coords}/forecast")
    suspend fun getForecast(
        @Path("office") office: String,
        @Path(value = "coords", encoded = true) coords: String,
        @Header("If-None-Match") ifNoneMatch: String? = null,
    ): Response<ForecastResponseDto>
}
