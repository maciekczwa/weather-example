package com.maciekczwa.weather.data.repository

import com.maciekczwa.weather.data.network.WeatherApi
import com.maciekczwa.weather.domain.model.LocationEntity
import com.maciekczwa.weather.domain.repository.WeatherRepository
import toEntity

class WeatherRepositoryImpl(private val weatherApi: WeatherApi, private val apiKey: String) : WeatherRepository {
    override suspend fun getWeather(location: LocationEntity) =
        weatherApi.getWeather(location.latitude, location.longitude, apiKey).toEntity()
}
