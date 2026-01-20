package com.m68476521.giphiertwo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

val LightColors =
    lightColorScheme(
        primary = theme_light_primary,
        onPrimary = theme_light_onPrimary,
        primaryContainer = theme_light_primaryContainer,
        secondary = theme_light_secondary,
        onSecondary = theme_light_onSecondary,
        background = theme_light_background,
        surfaceContainer = theme_light_onPrimaryContainer,
        surface = theme_light_surface,
        onSurface = theme_light_onSurface,
    )

val DarkColors =
    darkColorScheme(
        primary = theme_dark_primary,
        onPrimary = theme_dark_onPrimary,
        primaryContainer = theme_dark_primaryContainer,
        secondary = theme_dark_secondary,
        onSecondary = theme_dark_onSecondary,
        onBackground = theme_dark_background,
        surfaceContainer = theme_dark_onPrimaryContainer,
        surface = theme_dark_surface,
        onSurface = theme_dark_onSurface,
    )

@Composable
fun GiphiertwoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ but turned off for training purposes
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColors
            else -> LightColors
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
