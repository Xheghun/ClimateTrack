package com.xheghun.climatetrack.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.xheghun.climatetrack.presentation.R
import com.xheghun.climatetrack.presentation.screens.HomeViewModel

@Composable
fun WeatherSearchResult(model: HomeViewModel, onItemPressed: () -> Unit) {
     model.searchQuery
    LazyColumn(Modifier.fillMaxSize()) {
        items(3) {
            SearchItem(onItemPressed::invoke)
        }
    }
}

@Composable
private fun SearchItem(onItemPressed: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onItemPressed.invoke()}
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Mumbai",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.displayMedium
            )
            Box(modifier = Modifier.height(5.dp))
            Row {
                Text(
                    text = "31",
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
        }
        Box(Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.cloudy), contentDescription = "")
    }
}