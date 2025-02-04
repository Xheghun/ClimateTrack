package com.xheghun.climatetrack.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xheghun.climatetrack.domain.model.City
import com.xheghun.climatetrack.domain.model.Weather
import com.xheghun.climatetrack.domain.usecase.FetchCityWeatherUseCase
import com.xheghun.climatetrack.domain.usecase.FetchFavouriteCityWeatherUseCase
import com.xheghun.climatetrack.domain.usecase.FetchSimilarCityUseCase
import com.xheghun.climatetrack.domain.usecase.SaveFavouriteCityWeatherUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
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
    private val saveFavCityWeatherUseCase: SaveFavouriteCityWeatherUseCase,
    private val fetchSimilarCityUseCase: FetchSimilarCityUseCase
) : ViewModel() {

    init {
        init()
    }

    private val _similarCities = MutableStateFlow<List<City>>(listOf())
    val similarCities = _similarCities.asStateFlow()

    private val _screenState: MutableStateFlow<WeatherScreenState> =
        MutableStateFlow(WeatherScreenState.Loading)

    val screenState = _screenState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchQueryDebounce = _searchQuery.debounce(1000)
        .filter { it.isNotEmpty() && it.length >= 2 }
        .distinctUntilChanged()
        .flatMapLatest {
            flow<Unit> {
                getSimilarCities(it.trim())
            }
        }.launchIn(viewModelScope)

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query

        if (query.isEmpty()) {
            updateScreenState(WeatherScreenState.Empty)
        } else {
            updateScreenState(WeatherScreenState.Search(null))
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


    private suspend fun getSimilarCities(query: String) {
        fetchSimilarCityUseCase.invoke(query).onSuccess {
            _similarCities.value = it
        }.onFailure {
            Log.e("", "error fetching", it)
        }
    }

    suspend fun fetchWeather(query: String) {
        fetchCityWeatherUseCase.invoke(query).onSuccess { result ->
            result?.let {
                updateScreenState(WeatherScreenState.Search(it))
            }
        }.onFailure {
            updateScreenState(WeatherScreenState.Search(error = "please check your internet connection"))
            Log.e("HomeViewModel", "Error making request", it)
        }

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
                Log.e("", "Error Retrieving From cache", it)
                updateScreenState(WeatherScreenState.Empty)
            }

        }
    }
}