package com.xheghun.climatetrack.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xheghun.climatetrack.domain.model.Weather
import com.xheghun.climatetrack.presentation.R

@Composable
fun WeatherInfo(weather: Weather) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = resolveIconRes(weather.current.tempC)),
            contentDescription = "",
            modifier = Modifier.padding(vertical = 10.dp).size(100.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                weather.location.name,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Box(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.location_icon),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "",
                modifier = Modifier.size(15.dp)
            )
        }

        Row {
            Text(
                text = "${weather.current.tempC}",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                painter = painterResource(id = R.drawable.ellipse),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "",
                modifier = Modifier.padding(10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(16.dp)
        ) {
            Info(
                title = "Humidity",
                info = "${weather.current.humidity}",
                modifier = Modifier.weight(1f)
            )
            Info(title = "UV", info = "${weather.current.uvValue.toInt()}", modifier = Modifier.weight(1f))
            Info(
                title = "Feels Like",
                info = "${weather.current.feelsLikeCelsius.toInt()}",
                modifier = Modifier.weight(1f),
                showDegrees = true
            )
        }
    }
}

@Composable
fun Info(title: String, info: String, modifier: Modifier = Modifier, showDegrees: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(10.dp)) {
        Text(
            title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall
        )
        Box(Modifier.height(10.dp))
        Row {
            Text(
                info,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall
            )

            if (showDegrees) {
                Icon(
                    painter = painterResource(id = R.drawable.ellipse),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(4.dp)
                )
            }
        }
    }
}

fun resolveIconRes(temp: Int): Int {
    return if (temp >= 25) {
        R.drawable.sunny
    } else if (temp >= 10) {
        R.drawable.cloudy
    } else {
        R.drawable.cool
    }
}