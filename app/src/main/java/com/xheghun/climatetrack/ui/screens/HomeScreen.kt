package com.xheghun.climatetrack.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.xheghun.climatetrack.R
import com.xheghun.climatetrack.ui.composables.SearchTextField
import com.xheghun.climatetrack.ui.composables.WeatherInfo
import com.xheghun.climatetrack.ui.theme.climateTrackCardBg
import com.xheghun.climatetrack.ui.theme.climateTrackGrey
import com.xheghun.climatetrack.ui.theme.climateTrackGreyLight
import com.xheghun.climatetrack.ui.theme.climateTrackPrimary

@Composable
fun HomeScreen(modifier: Modifier) {

    var isEmpty by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(12.dp)) {
        SearchTextField(
            value = "",
            onValueChange = {},
            hintText = "Search location"
        )

        //BODY
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 20.dp)
        ) {
            AnimatedVisibility(visible = isEmpty) {
                EmptyState()
            }

            AnimatedVisibility(visible = !isEmpty) {
                WeatherInfo()
            }
        }
    }
}
