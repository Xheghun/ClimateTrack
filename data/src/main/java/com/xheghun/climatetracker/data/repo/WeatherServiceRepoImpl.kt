package com.xheghun.climatetracker.data.repo

import android.util.Log
import com.xheghun.climatetrack.domain.model.Weather
import com.xheghun.climatetrack.domain.repo.WeatherRepo
import com.xheghun.climatetracker.data.api.WeatherApiService
import com.xheghun.climatetracker.data.cache.Cache
import com.xheghun.climatetracker.data.models.toDomainWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WeatherServiceRepoImpl(
    private val weatherApiService: WeatherApiService,
    private val cache: Cache<Weather>,
    private val dispatcher: CoroutineDispatcher
) : WeatherRepo {
    override suspend fun getWeatherInfoByCity(city: String): Result<Weather?> =
        runCatching {
            withContext(dispatcher) {
                weatherApiService.getWeatherInfo(city).toDomainWeather()
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