package com.xheghun.climatetracker.data.repo

import com.xheghun.climatetracker.data.api.WeatherApiService
import com.xheghun.climatetracker.data.cache.Cache
import com.xheghun.climatetracker.data.models.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WeatherServiceRepoImpl(
    private val weatherApiService: WeatherApiService,
    private val cache: Cache<Weather>,
    private val dispatcher: CoroutineDispatcher
) :
    WeatherServiceRepository {
    override suspend fun getWeatherInfoFromApi(city: String): Result<Weather> =
        runCatching {
            withContext(dispatcher) {
                weatherApiService.getWeatherInfo(city)
            }
        }

    override suspend fun getFavouriteCityWeather(): Result<Weather?> =
        runCatching {
            withContext(dispatcher) {
                cache.get("favorite_city")
            }
        }

    override suspend fun saveFavouriteCityWeather(weather: Weather) = runCatching {
        withContext(dispatcher) {
            cache.put("favorite_city", weather)
        }
    }
}