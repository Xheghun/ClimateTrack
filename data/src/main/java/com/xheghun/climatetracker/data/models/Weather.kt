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
    val timezoneID: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
)

fun Condition.toDomainCondition(): com.xheghun.climatetrack.domain.model.Condition {
    return com.xheghun.climatetrack.domain.model.Condition(
        text = text ?: "",
        icon = icon ?: "",
        code = code ?: 0
    )
}

fun Current.toDomainCurrent(): com.xheghun.climatetrack.domain.model.Current {
    return com.xheghun.climatetrack.domain.model.Current(
        lastUpdated = lastUpdated ?: "",
        tempC = tempC?.toInt() ?: 0,
        condition = condition?.toDomainCondition()
            ?: com.xheghun.climatetrack.domain.model.Condition("", "", 0),
        humidity = humidity ?: 0,
        feelsLikeCelsius = feelsLikeCelsius?.toInt() ?: 0,
        uv = uv?.toInt() ?: 0
    )
}

fun Location.toDomainLocation(): com.xheghun.climatetrack.domain.model.Location {
    return com.xheghun.climatetrack.domain.model.Location(
        name = name ?: "",
        region = region ?: "",
        country = country ?: "",
        timezoneID = timezoneID ?: "",
        lat = lat ?: 0.0,
        lon = lon ?: 0.0
    )
}

fun Weather.toDomainWeather(): com.xheghun.climatetrack.domain.model.Weather? {
    location?.let { location ->
        current?.let { current ->
            return com.xheghun.climatetrack.domain.model.Weather(
                location = location.toDomainLocation(),
                current = current.toDomainCurrent()
            )
        }
    }
    return null
}