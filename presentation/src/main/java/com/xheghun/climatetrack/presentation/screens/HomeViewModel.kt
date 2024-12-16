package com.xheghun.climatetrack.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xheghun.climatetrack.domain.model.Weather
import com.xheghun.climatetrack.domain.usecase.FetchCityWeatherUseCase
import com.xheghun.climatetrack.domain.usecase.FetchFavouriteCityWeatherUseCase
import com.xheghun.climatetrack.domain.usecase.SaveFavouriteCityWeatherUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

sealed class WeatherScreenState {
    data object Empty : WeatherScreenState()
    data object Loading : WeatherScreenState()
    data class Search(val result: Weather? = null, val error: String? = null) : WeatherScreenState()
    data class FavCity(val weather: Weather) : WeatherScreenState()
}

class HomeViewModel(
    private val fetchCityWeatherUseCase: FetchCityWeatherUseCase,
    private val fetchFavCityWeatherUseCase: FetchFavouriteCityWeatherUseCase,
    private val saveFavCityWeatherUseCase: SaveFavouriteCityWeatherUseCase
) : ViewModel() {

    init {
        init()
    }

    private val _screenState: MutableStateFlow<WeatherScreenState> =
        MutableStateFlow(WeatherScreenState.Loading)

    val screenState = _screenState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query.trim()

        if (query.isEmpty()) {
            updateScreenState(WeatherScreenState.Empty)
        } else {
            updateScreenState(WeatherScreenState.Search(null))
            fetchWeather()
        }
    }

    fun updateScreenState(state: WeatherScreenState) {
        if (_screenState.value != state) {
            _screenState.value = state
        }
    }

    fun saveFavCity(weather: Weather) {
        viewModelScope.launch {
            saveFavCityWeatherUseCase.invoke(weather)
        }
    }

    @OptIn(FlowPreview::class)
    private fun fetchWeather() {
        searchQuery.debounce(300)
            .filter { it.isNotEmpty() && it.length >= 2 }
            .distinctUntilChanged()
            .onEach {
                fetchCityWeatherUseCase.invoke(searchQuery.value).onSuccess { result ->
                    result?.let {
                        updateScreenState(WeatherScreenState.Search(it))
                    }
                }.onFailure {
                    updateScreenState(WeatherScreenState.Search(error = "please check your internet connection"))
                    Log.e("HomeViewModel", "Error making request", it)
                }
            }.launchIn(viewModelScope)
    }

    private fun init() {
        viewModelScope.launch {
            fetchFavCityWeatherUseCase.invoke().onSuccess { weather ->
                Log.d("", "Retrieved From cache $weather")
                if (weather != null) {
                    updateScreenState(WeatherScreenState.FavCity(weather))
                    //get latest update
                    fetchCityWeatherUseCase.invoke("${weather.location.lat},${weather.location.lon}")
                        .onSuccess {
                            it?.let {
                                saveFavCity(it)
                                updateScreenState(WeatherScreenState.FavCity(it))
                            }
                        }
                } else {
                    updateScreenState(WeatherScreenState.Empty)
                }
            }.onFailure {
                Log.d("", "Error Retrieving From cache $it")
                updateScreenState(WeatherScreenState.Empty)
            }

        }
    }
}