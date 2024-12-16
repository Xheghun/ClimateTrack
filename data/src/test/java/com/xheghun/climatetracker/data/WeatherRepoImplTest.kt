package com.xheghun.climatetracker.data

import com.xheghun.climatetrack.domain.model.Condition
import com.xheghun.climatetrack.domain.model.Current
import com.xheghun.climatetrack.domain.model.Location
import com.xheghun.climatetrack.domain.model.Weather
import com.xheghun.climatetracker.data.api.WeatherApiService
import com.xheghun.climatetracker.data.cache.Cache
import com.xheghun.climatetracker.data.repo.WeatherServiceRepoImpl
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WeatherRepoImplTest {
    private val mockDataWeather = com.xheghun.climatetracker.data.models.Weather(
        location = com.xheghun.climatetracker.data.models.Location(
            "London",
            "UK",
            "England",
            "",
            291.0,
            112.0
        ),
        current = com.xheghun.climatetracker.data.models.Current(
            "", 15.0,
            com.xheghun.climatetracker.data.models.Condition("Cool", "", 0),
            33,
            18.0,
            0.0
        )
    )

    private val mockWeather = Weather(
        location = Location(
            "London",
            "UK",
            "England",
            "",
            291.0,
            112.0
        ),
        current = Current(
            "", 15,
            Condition("Cool", "", 0),
            33,
            18.0,
            0.0
        )
    )

    private lateinit var weatherApiService: WeatherApiService
    private lateinit var cache: Cache<Weather>
    private lateinit var repo: WeatherServiceRepoImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        weatherApiService = mockk()
        cache = mockk()
        repo = WeatherServiceRepoImpl(weatherApiService, cache, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getWeatherInfoByCity should return weather info from API`() = runTest {
        coEvery { weatherApiService.getWeatherInfo("London") } returns mockDataWeather

        val result = repo.getWeatherInfoByCity("London")

        assert(result.isSuccess)
        assertEquals(mockWeather, result.getOrNull())
        coVerify { weatherApiService.getWeatherInfo("London") }
    }

    @Test
    fun `getWeatherInfoByCity should handle API exceptions`() = runTest {
        val exception: Throwable = Exception("API Error")
        coEvery { weatherApiService.getWeatherInfo("London") } throws exception

        val result = repo.getWeatherInfoByCity("London")

        assert(result.isFailure)
        val actualException = result.exceptionOrNull()

        assert(actualException is Exception)
        assertEquals("API Error", actualException?.message)
    }

    @Test
    fun `getFavouriteCityWeather should return weather info from cache`() = runTest {
        coEvery { cache.get("favorite_city") } returns mockWeather

        val result = repo.getFavouriteCityWeather()

        assert(result.isSuccess)
        assertEquals(mockWeather, result.getOrNull())
        coVerify { cache.get("favorite_city") }
    }

    @Test
    fun `getFavouriteCityWeather should handle cache miss`() = runTest {
        coEvery { cache.get("favorite_city") } returns null
        val result = repo.getFavouriteCityWeather()

        assert(result.isSuccess)
        assertEquals(null, result.getOrNull())
        coVerify { cache.get("favorite_city") }
    }

    @Test
    fun `saveFavouriteCityWeather should save weather info in cache`() = runTest {
        coEvery { cache.put("favorite_city", mockWeather) } just Runs
        val result = repo.saveFavouriteCityWeather(mockWeather)

        assert(result.isSuccess)
        coVerify { cache.put("favorite_city", mockWeather) }
    }

    @Test
    fun `saveFavouriteCityWeather should handle cache exceptions`() = runTest {
        val exception = Exception("Cache Error")
        coEvery { cache.put("favorite_city", mockWeather) } throws exception
        val result = repo.saveFavouriteCityWeather(mockWeather)

        val actualException = result.exceptionOrNull()

        assert(actualException is Exception)
        assertEquals("Cache Error", actualException?.message)

        coVerify { cache.put("favorite_city", mockWeather) }
    }
}