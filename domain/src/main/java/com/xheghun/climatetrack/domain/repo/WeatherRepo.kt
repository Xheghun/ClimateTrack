package com.xheghun.climatetrack.domain.repo

import com.xheghun.climatetrack.domain.model.City
import com.xheghun.climatetrack.domain.model.Weather

interface WeatherRepo {
    suspend fun getSimilarCities(city: String): Result<List<City>>
    suspend fun getWeatherInfoByCity(city: String): Result<Weather?>
    suspend fun getFavouriteCityWeather(): Result<Weather?>
    suspend fun saveFavouriteCityWeather(weather: Weather): Result<Unit>
}