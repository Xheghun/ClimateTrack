package com.xheghun.climatetrack.presentation.screens

import androidx.lifecycle.ViewModel
import com.xheghun.climatetrack.domain.usecase.FetchCityWeatherUseCase
import com.xheghun.climatetrack.domain.usecase.FetchFavouriteCityWeatherUseCase
import com.xheghun.climatetrack.domain.usecase.SaveFavouriteCityWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class WeatherScreenState {
    data object Empty : WeatherScreenState()
    data object Search : WeatherScreenState()
    data object Success : WeatherScreenState()
}

class HomeViewModel(
    private val fetchCityWeatherUseCase: FetchCityWeatherUseCase,
    private val fetchFavCityWeatherUseCase: FetchFavouriteCityWeatherUseCase,
    private val saveFavCityWeatherUseCase: SaveFavouriteCityWeatherUseCase
) : ViewModel() {
    private val _screenState: MutableStateFlow<WeatherScreenState> =
        MutableStateFlow(WeatherScreenState.Empty)

    val screenState = _screenState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        if (query.isEmpty()) {
            updateScreenState(WeatherScreenState.Empty)
        } else {
            updateScreenState(WeatherScreenState.Search)
        }
        _searchQuery.value = query.trim()
    }

    fun updateScreenState(state: WeatherScreenState) {
        if (_screenState.value != state) {
            _screenState.value = state
        }
    }
}