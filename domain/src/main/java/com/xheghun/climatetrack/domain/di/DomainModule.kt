package com.xheghun.climatetrack.domain.di

import com.xheghun.climatetrack.domain.usecase.FetchCityWeatherUseCase
import com.xheghun.climatetrack.domain.usecase.FetchFavouriteCityWeatherUseCase
import com.xheghun.climatetrack.domain.usecase.SaveFavouriteCityWeatherUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { FetchCityWeatherUseCase(get()) }
    factory { FetchFavouriteCityWeatherUseCase(get()) }
    factory { SaveFavouriteCityWeatherUseCase(get()) }
}