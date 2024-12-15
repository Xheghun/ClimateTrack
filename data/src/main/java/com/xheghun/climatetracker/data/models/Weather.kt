package com.xheghun.climatetracker.data.models

import com.google.gson.annotations.SerializedName

data class Weather(
    val location: Location? = null,
    val current: Current? = null
)

data class Current(
    @SerializedName("last_updated")
    val lastUpdated: String? = null,
    @SerializedName("temp_c")
    val tempC: Double? = null,
    val condition: Condition? = null,
    val humidity: Long? = null,
    @SerializedName("feelslike_c")
    val feelsLikeCelsius: Double? = null,
    val uv: Double? = null
)

data class Condition(
    val text: String? = null,
    val icon: String? = null,
    val code: Long? = null
)

data class Location(
    val name: String? = null,
    val region: String? = null,
    val country: String? = null,
    @SerializedName("tz_id")
    val timezoneID: String? = null
)
