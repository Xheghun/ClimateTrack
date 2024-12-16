package com.xheghun.climatetrack.domain

import com.xheghun.climatetrack.domain.model.Condition
import com.xheghun.climatetrack.domain.model.Current
import com.xheghun.climatetrack.domain.model.Location
import com.xheghun.climatetrack.domain.model.Weather
import com.xheghun.climatetrack.domain.repo.WeatherRepo
import com.xheghun.climatetrack.domain.usecase.FetchCityWeatherUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

val dummyWeather = Weather(
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

class FetchCityWeatherTest {
    private lateinit var repo: WeatherRepo
    private lateinit var fetchCityWeatherUseCase: FetchCityWeatherUseCase

    @Before
    fun setup() {
        repo = mockk()
        fetchCityWeatherUseCase = FetchCityWeatherUseCase(repo)
    }

    fun `invoke should call getWeatherInfoByCity on the repository`() = runTest {
        val mockWeather = dummyWeather
        coEvery { repo.getWeatherInfoByCity("London") } returns Result.success(mockWeather)

        val result = fetchCityWeatherUseCase("London")

        coVerify { repo.getWeatherInfoByCity("London") }
        assertEquals(Result.success(mockWeather), result)
    }

    @Test
    fun `invoke should return an error if repository throws an exception`() = runTest {
        val exception = Exception("Network error")
        coEvery { repo.getWeatherInfoByCity("London") } throws exception

        val result = runCatching { fetchCityWeatherUseCase("London") }

        assert(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}