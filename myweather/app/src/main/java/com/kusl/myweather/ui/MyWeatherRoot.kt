package com.kusl.myweather.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kusl.myweather.R
import com.kusl.myweather.ui.dashboard.DashboardScreen
import com.kusl.myweather.ui.dashboard.DashboardViewModel
import com.kusl.myweather.ui.locations.LocationsScreen
import com.kusl.myweather.ui.locations.LocationsViewModel
import com.kusl.myweather.ui.settings.SettingsScreen
import com.kusl.myweather.ui.settings.SettingsViewModel

private sealed class Dest(val route: String, val labelRes: Int, val icon: ImageVector) {
    data object Dashboard : Dest("dashboard?lat={lat}&lon={lon}", R.string.nav_dashboard, AppIcons.LocationOn) {
        const val BASE = "dashboard"
        fun withCoords(lat: Double, lon: Double) = "$BASE?lat=$lat&lon=$lon"
    }
    data object Locations : Dest("locations", R.string.nav_locations, AppIcons.ListView)
    data object Settings : Dest("settings", R.string.nav_settings, AppIcons.Tune)
}

private val bottomDestinations = listOf(Dest.Dashboard, Dest.Locations, Dest.Settings)

@Composable
fun MyWeatherRoot(viewModelFactory: ViewModelProvider.Factory) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route
            NavigationBar {
                bottomDestinations.forEach { dest ->
                    NavigationBarItem(
                        selected = isCurrent(currentRoute, dest),
                        onClick = {
                            val target = if (dest is Dest.Dashboard) Dest.Dashboard.BASE else dest.route
                            navController.navigate(target) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(dest.icon, contentDescription = null) },
                        label = { Text(stringResource(dest.labelRes)) },
                    )
                }
            }
        },
    ) { innerPadding ->
        // Only consume the bottom inset here; each screen's TopAppBar handles the top.
        val bodyModifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())

        NavHost(
            navController = navController,
            startDestination = Dest.Dashboard.route,
            modifier = bodyModifier,
        ) {
            composable(
                route = Dest.Dashboard.route,
                arguments = listOf(
                    navArgument("lat") { type = NavType.StringType; nullable = true; defaultValue = null },
                    navArgument("lon") { type = NavType.StringType; nullable = true; defaultValue = null },
                ),
            ) { entry ->
                val vm = viewModel<DashboardViewModel>(factory = viewModelFactory)
                val lat = entry.arguments?.getString("lat")?.toDoubleOrNull()
                val lon = entry.arguments?.getString("lon")?.toDoubleOrNull()
                DashboardScreen(viewModel = vm, presetLat = lat, presetLon = lon)
            }

            composable(Dest.Locations.route) {
                val vm = viewModel<LocationsViewModel>(factory = viewModelFactory)
                LocationsScreen(
                    viewModel = vm,
                    onShowOnDashboard = { loc ->
                        navController.navigate(Dest.Dashboard.withCoords(loc.latitude, loc.longitude)) {
                            launchSingleTop = true
                        }
                    },
                )
            }

            composable(Dest.Settings.route) {
                val vm = viewModel<SettingsViewModel>(factory = viewModelFactory)
                SettingsScreen(viewModel = vm)
            }
        }
    }
}

private fun isCurrent(currentRoute: String?, dest: Dest): Boolean = when (dest) {
    is Dest.Dashboard ->
        currentRoute == Dest.Dashboard.route || currentRoute?.startsWith(Dest.Dashboard.BASE) == true
    else -> currentRoute == dest.route
}
