package com.xheghun.climatetracker.data.repo

import com.xheghun.climatetracker.data.models.Weather

interface WeatherServiceRepository {
    suspend fun getWeatherInfoFromApi(city: String): Result<Weather>
    suspend fun getFavouriteCityWeather(): Result<Weather?>
    suspend fun saveFavouriteCityWeather(weather: Weather): Result<Unit>
}