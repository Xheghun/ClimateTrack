package com.xheghun.climatetrack.domain.usecase

import com.xheghun.climatetrack.domain.model.Weather

class FetchCityWeatherUseCase(private val repo: WeatherRepo) {
    suspend operator fun invoke(location: String): Weather = repo.getCityWeather(location)
}

class FetchFavouriteCityWeatherUseCase(private val repo: WeatherRepo) {
    suspend operator fun invoke(): Weather? = repo.getFavouriteCityWeather()
}

class SaveFavouriteCityWeatherUseCase(private val repo: WeatherRepo) {
    suspend operator fun invoke(weather: Weather) = repo.saveFavouriteCityWeather(weather)
}

interface WeatherRepo {
    suspend fun getCityWeather(location: String): Weather
    suspend fun getFavouriteCityWeather(): Weather?
    suspend fun saveFavouriteCityWeather(weather: Weather)
}