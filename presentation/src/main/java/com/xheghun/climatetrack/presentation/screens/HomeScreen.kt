package com.xheghun.climatetrack.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xheghun.climatetrack.presentation.composables.EmptyState
import com.xheghun.climatetrack.presentation.composables.WeatherSearchResult
import com.xheghun.climatetrack.presentation.composables.SearchTextField
import com.xheghun.climatetrack.presentation.composables.WeatherInfo
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier) {
    val model = koinViewModel<HomeViewModel>()
    val screenState = model.screenState.collectAsStateWithLifecycle().value

    val keyboardController = LocalSoftwareKeyboardController.current
    val similarCities = model.similarCities.collectAsStateWithLifecycle().value

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier.padding(12.dp)) {
        SearchTextField(
            value = model.searchQuery.collectAsStateWithLifecycle().value,
            onValueChange = {
                model.updateSearchQuery(it)
            },
            hintText = "Search location"
        )

        //SIMILAR CITIES
        LazyRow {
            items(similarCities.size) { index ->
                val city = similarCities[index]

                Text(city.name,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(8.dp)
                        .border(1.dp, MaterialTheme.colorScheme.onPrimary)
                        .clickable {
                            coroutineScope.launch {
                                model.fetchWeather(city.name)
                            }
                        }
                        .padding(4.dp)

                )

            }
        }

        //BODY
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
            AnimatedVisibility(visible = screenState == WeatherScreenState.Empty) {
                EmptyState()
            }

            AnimatedVisibility(visible = screenState is WeatherScreenState.FavCity) {
                val faveCity = screenState as? WeatherScreenState.FavCity
                faveCity?.let {
                    WeatherInfo(it.weather)
                }
            }

            AnimatedVisibility(
                visible = screenState is WeatherScreenState.Search,
            ) {
                WeatherSearchResult(model) {
                    model.updateScreenState(WeatherScreenState.FavCity(it))
                    model.saveFavCity(it)
                    keyboardController?.hide()
                }
            }
        }
    }
}
