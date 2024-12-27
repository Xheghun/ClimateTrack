package com.xheghun.climatetracker.data.api

import com.xheghun.climatetrack.domain.model.City
import com.xheghun.climatetracker.data.models.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current.json")
    suspend fun getWeatherInfo(@Query("q") query: String): Weather

    @GET("search.json")
    suspend fun getSimilarCity(@Query("q") query: String): List<City>
}