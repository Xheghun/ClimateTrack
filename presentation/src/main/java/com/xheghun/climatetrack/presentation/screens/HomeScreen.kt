package com.xheghun.climatetrack.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xheghun.climatetrack.presentation.composables.EmptyState
import com.xheghun.climatetrack.presentation.composables.WeatherSearchResult
import com.xheghun.climatetrack.presentation.composables.SearchTextField
import com.xheghun.climatetrack.presentation.composables.WeatherInfo

@Composable
fun HomeScreen(modifier: Modifier) {
    val model = viewModel<HomeViewModel>()

    Column(modifier = modifier.padding(12.dp)) {
        SearchTextField(
            value = model.searchQuery.collectAsState().value,
            onValueChange = {
                model.updateSearchQuery(it)
            },
            hintText = "Search location"
        )

        //BODY
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
            AnimatedVisibility(visible = model.screenState.collectAsState().value == WeatherScreenState.Empty) {
                EmptyState()
            }

            AnimatedVisibility(visible = model.screenState.collectAsState().value == WeatherScreenState.Success) {
                WeatherInfo()
            }

            AnimatedVisibility(visible = model.screenState.collectAsState().value == WeatherScreenState.Search) {
                WeatherSearchResult(model) {
                    model.updateScreenState(WeatherScreenState.Success)
                }
            }
        }
    }
}
