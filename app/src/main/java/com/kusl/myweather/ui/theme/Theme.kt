package com.kusl.myweather.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = SkyBlue,
    secondary = NightInk,
    tertiary = Sunlit,
    background = Mist,
)

private val DarkColors = darkColorScheme(
    primary = SkyBlueDark,
    secondary = Mist,
    tertiary = Sunlit,
)

@Composable
fun MyWeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Android 12+ (always true at minSdk 34) can derive a wallpaper-based scheme.
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val ctx = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MyWeatherTypography,
        content = content,
    )
}
