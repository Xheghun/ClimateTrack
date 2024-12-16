package com.xheghun.climatetrack.domain.model

data class Weather(
    val location: Location,
    val current: Current
)

data class Current(
    val lastUpdated: String,
    val tempC: Int,
    val condition: Condition,
    val humidity: Long,
    val feelsLikeCelsius: Double,
    val uvValue: Double
)

data class Condition(
    val text: String,
    val icon: String,
    val code: Long
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val timezoneID: String,
    val lat: Double,
    val lon: Double
)
