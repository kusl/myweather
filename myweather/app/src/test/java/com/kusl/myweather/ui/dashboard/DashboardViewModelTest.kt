package com.kusl.myweather.ui.dashboard

import com.kusl.myweather.core.GeoPoint
import com.kusl.myweather.core.GridPoint
import com.kusl.myweather.data.location.LocationResult
import com.kusl.myweather.data.location.LocationSource
import com.kusl.myweather.data.settings.SettingsRepository
import com.kusl.myweather.domain.AreaWeatherResult
import com.kusl.myweather.domain.WeatherRepository
import com.kusl.myweather.domain.model.AreaWeather
import com.kusl.myweather.domain.model.Forecast
import com.kusl.myweather.domain.model.ForecastPeriod
import com.kusl.myweather.domain.model.PointMetadata
import com.kusl.myweather.domain.model.WeatherTile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before fun setUp() = Dispatchers.setMain(dispatcher)
    @After fun tearDown() = Dispatchers.resetMain()

    private fun viewModel(
        repo: WeatherRepository,
        location: LocationSource = FakeLocationSource(),
        radius: Int = 1,
    ) = DashboardViewModel(repo, location, FakeSettings(radius))

    @Test
    fun `successful load exposes area and clears message`() = runTest(dispatcher) {
        val vm = viewModel(FakeWeatherRepository(AreaWeatherResult.Success(sampleArea(fromCache = false))))

        vm.load(GeoPoint(36.85, -76.28))
        advanceUntilIdle()

        val state = vm.uiState.value
        assertFalse(state.isLoading)
        assertNotNull(state.area)
        assertFalse(state.offline)
        assertNull(state.message)
    }

    @Test
    fun `offline flag follows fromCache`() = runTest(dispatcher) {
        val vm = viewModel(FakeWeatherRepository(AreaWeatherResult.Success(sampleArea(fromCache = true))))
        vm.load(GeoPoint(36.85, -76.28))
        advanceUntilIdle()
        assertTrue(vm.uiState.value.offline)
    }

    @Test
    fun `no coverage produces a message and no area`() = runTest(dispatcher) {
        val vm = viewModel(FakeWeatherRepository(AreaWeatherResult.NoCoverage))
        vm.load(GeoPoint(0.0, 0.0))
        advanceUntilIdle()
        assertNull(vm.uiState.value.area)
        assertNotNull(vm.uiState.value.message)
    }

    @Test
    fun `unavailable surfaces the repository message`() = runTest(dispatcher) {
        val vm = viewModel(FakeWeatherRepository(AreaWeatherResult.Unavailable("boom")))
        vm.load(GeoPoint(36.85, -76.28))
        advanceUntilIdle()
        assertEquals("boom", vm.uiState.value.message)
    }

    @Test
    fun `invalid manual entry sets a validation message without calling the repo`() = runTest(dispatcher) {
        val repo = FakeWeatherRepository(AreaWeatherResult.Success(sampleArea(false)))
        val vm = viewModel(repo)
        vm.loadManual("not coordinates")
        advanceUntilIdle()
        assertNotNull(vm.uiState.value.message)
        assertEquals(0, repo.callCount)
    }

    @Test
    fun `denied location permission updates status`() = runTest(dispatcher) {
        val vm = viewModel(
            FakeWeatherRepository(AreaWeatherResult.Success(sampleArea(false))),
            location = FakeLocationSource(permission = true, result = LocationResult.PermissionDenied),
        )
        vm.requestDeviceLocation()
        advanceUntilIdle()
        assertEquals(LocationStatus.PermissionDenied, vm.uiState.value.locationStatus)
    }

    @Test
    fun `available location triggers a load`() = runTest(dispatcher) {
        val repo = FakeWeatherRepository(AreaWeatherResult.Success(sampleArea(false)))
        val vm = viewModel(
            repo,
            location = FakeLocationSource(
                permission = true,
                result = LocationResult.Available(GeoPoint(36.85, -76.28)),
            ),
        )
        vm.requestDeviceLocation()
        advanceUntilIdle()
        assertEquals(1, repo.callCount)
        assertNotNull(vm.uiState.value.area)
    }
}

private fun sampleArea(fromCache: Boolean): AreaWeather {
    val grid = GridPoint("AKQ", 83, 61)
    val period = ForecastPeriod(
        number = 1, name = "This Afternoon", startTime = "", endTime = "", isDaytime = true,
        temperature = 81, temperatureUnit = "F", probabilityOfPrecipitation = 20,
        windSpeed = "7 mph", windDirection = "SW", shortForecast = "Sunny",
        detailedForecast = "Sunny.", icon = "",
    )
    val forecast = Forecast("AKQ", 83, 61, null, null, listOf(period))
    val meta = PointMetadata(36.85, -76.28, "AKQ", 83, 61, "Norfolk", "VA", "America/New_York")
    val tile = WeatherTile(grid, forecast, isPrimary = true, distanceMeters = 0.0)
    return AreaWeather(GeoPoint(36.85, -76.28), meta, forecast, listOf(tile), fromCache)
}

private class FakeWeatherRepository(private val result: AreaWeatherResult) : WeatherRepository {
    var callCount = 0
    override suspend fun getAreaWeather(point: GeoPoint, radius: Int): AreaWeatherResult {
        callCount++
        return result
    }
}

private class FakeLocationSource(
    private val permission: Boolean = false,
    private val result: LocationResult = LocationResult.Unavailable,
) : LocationSource {
    override fun hasPermission(): Boolean = permission
    override suspend fun currentLocation(): LocationResult = result
}

private class FakeSettings(private val radius: Int) : SettingsRepository {
    override val userAgent: Flow<String> = flowOf("MyWeather/test")
    override val neighborhoodRadius: Flow<Int> = flowOf(radius)
    override suspend fun setUserAgent(value: String) = Unit
    override suspend fun setNeighborhoodRadius(value: Int) = Unit
}
