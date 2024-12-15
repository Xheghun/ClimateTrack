package com.xheghun.climatetrack.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.xheghun.climatetrack.presentation.theme.ClimateTrackTheme

@Composable
fun ClimateTrack() {
    ClimateTrackTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            com.xheghun.climatetrack.presentation.screens.HomeScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}