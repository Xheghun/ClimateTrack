package com.xheghun.climatetrack.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class WeatherScreenState {
    data object Empty : WeatherScreenState()
    data object Search : WeatherScreenState()
    data object Success : WeatherScreenState()
}

class HomeViewModel : ViewModel() {
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