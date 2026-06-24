package com.kusl.myweather.data.remote

import com.kusl.myweather.data.remote.dto.AlertsResponseDto
import com.kusl.myweather.data.remote.dto.ForecastResponseDto
import com.kusl.myweather.data.remote.dto.ObservationResponseDto
import com.kusl.myweather.data.remote.dto.PointResponseDto
import com.kusl.myweather.data.remote.dto.StationsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Typed view over the public NWS API. Returns Retrofit [Response] (not the bare
 * body) so the repository can read the `ETag` / `Cache-Control` headers and
 * handle `304 Not Modified` — the cheap-refresh path of our cache.
 *
 * Coordinate/grid path segments are passed pre-encoded so the literal commas
 * (`lat,lon`, `x,y`) reach NWS unescaped, exactly as the API expects.
 *
 * Every call here transparently flows through [HttpCacheInterceptor], so each
 * endpoint — including the supplementary ones below — is de-duplicated,
 * negative-cached, and served stale-on-error without any per-call work.
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

    /**
     * Active watches/warnings/advisories for a coordinate. `point` is `"lat,lon"`.
     * Same `/alerts/active` query the NWS site uses; returns a GeoJSON
     * FeatureCollection of alerts (often empty).
     */
    @GET("alerts/active")
    suspend fun getActiveAlerts(
        @Query(value = "point", encoded = true) point: String,
    ): Response<AlertsResponseDto>

    /**
     * The hour-by-hour forecast for a grid cell — same payload shape as
     * [getForecast], just finer-grained periods. `coords` is `"gridX,gridY"`.
     */
    @GET("gridpoints/{office}/{coords}/forecast/hourly")
    suspend fun getForecastHourly(
        @Path("office") office: String,
        @Path(value = "coords", encoded = true) coords: String,
        @Header("If-None-Match") ifNoneMatch: String? = null,
    ): Response<ForecastResponseDto>

    /** The observation stations serving a grid cell. `coords` is `"gridX,gridY"`. */
    @GET("gridpoints/{office}/{coords}/stations")
    suspend fun getObservationStations(
        @Path("office") office: String,
        @Path(value = "coords", encoded = true) coords: String,
    ): Response<StationsResponseDto>

    /** The latest surface observation from a station, e.g. `"KAKQ"`. */
    @GET("stations/{stationId}/observations/latest")
    suspend fun getLatestObservation(
        @Path(value = "stationId", encoded = true) stationId: String,
    ): Response<ObservationResponseDto>
}
