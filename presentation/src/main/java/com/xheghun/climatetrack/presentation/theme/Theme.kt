package com.xheghun.climatetrack.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = climateTrackPrimary,
    surfaceContainer = climateTrackCardBg,
    onSurface = climateTrackGrey,
    onSurfaceVariant = climateTrackGreyLight,
    onPrimary = climateTrackBlack,
    background = climateTrackPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = climateTrackPrimary,
    surfaceContainer = climateTrackCardBg,
    onSurface = climateTrackGrey,
    onSurfaceVariant = climateTrackGreyLight,
    onPrimary = climateTrackBlack,
    background = climateTrackPrimary
)

@Composable
fun ClimateTrackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}