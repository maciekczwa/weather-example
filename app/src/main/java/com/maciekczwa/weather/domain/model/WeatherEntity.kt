package com.maciekczwa.weather.domain.model

data class WeatherEntity(
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    val temperature: Double,
)
