package com.maciekczwa.weather.domain.repository

import com.maciekczwa.weather.domain.model.LocationEntity
import com.maciekczwa.weather.domain.model.WeatherEntity

interface WeatherRepository {
    suspend fun getWeather(location: LocationEntity): WeatherEntity
}
