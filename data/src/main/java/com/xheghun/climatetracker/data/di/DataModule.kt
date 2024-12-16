package com.xheghun.climatetracker.data.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.xheghun.climatetrack.domain.model.Weather
import com.xheghun.climatetrack.domain.repo.WeatherRepo
import com.xheghun.climatetracker.data.BuildConfig
import com.xheghun.climatetracker.data.api.WeatherApiService
import com.xheghun.climatetracker.data.cache.Cache
import com.xheghun.climatetracker.data.cache.LocalCache
import com.xheghun.climatetracker.data.repo.WeatherServiceRepoImpl
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.weatherapi.com/v1/"

fun dataModule(context: Context) = module {

    //REPO
    single { WeatherServiceRepoImpl(get(), get(), get()) as WeatherRepo }

    single {
        HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    }

    //dispatcher
    single { Dispatchers.IO }

    //REQUEST INTERCEPTOR
    single {
        Interceptor { chain ->
            val originalRequest = chain.request();

            //add api key to every request
            val modifiedUrl = originalRequest.url.newBuilder()
                .addQueryParameter("key", BuildConfig.EXCHANGE_API_KEY)
                .build()

            val modifiedRequest = originalRequest.newBuilder()
                .url(modifiedUrl)
                .build()

            chain.proceed(modifiedRequest)
        }
    }

    //SERIALIZER
    single { GsonBuilder().create() }
    single { GsonConverterFactory.create(get()) } // gson converter

    //HTTP CLIENT
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    } // client
    single {
        Retrofit.Builder()
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get<OkHttpClient>())
            .baseUrl(BASE_URL)
            .build()
    } // retrofit
    single { get<Retrofit>().create(WeatherApiService::class.java) } // api service

    //CACHE
    single {
        LocalCache(
            context,
            get(),
            Weather::class.java,
            21_600_000 //6 hours
        ) as Cache<Weather>
    }
}