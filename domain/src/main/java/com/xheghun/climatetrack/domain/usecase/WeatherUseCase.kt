package com.xheghun.climatetrack.domain.usecase

import com.xheghun.climatetrack.domain.model.City
import com.xheghun.climatetrack.domain.model.Weather
import com.xheghun.climatetrack.domain.repo.WeatherRepo

class FetchCityWeatherUseCase(private val repo: WeatherRepo) {
    suspend operator fun invoke(location: String): Result<Weather?> =
        repo.getWeatherInfoByCity(location)
}

class FetchSimilarCityUseCase(private val repo: WeatherRepo) {
    suspend fun invoke(query: String): Result<List<City>> = repo.getSimilarCities(query)
}

class FetchFavouriteCityWeatherUseCase(private val repo: WeatherRepo) {
    suspend operator fun invoke(): Result<Weather?> = repo.getFavouriteCityWeather()
}

class SaveFavouriteCityWeatherUseCase(private val repo: WeatherRepo) {
    suspend operator fun invoke(weather: Weather) = repo.saveFavouriteCityWeather(weather)
}