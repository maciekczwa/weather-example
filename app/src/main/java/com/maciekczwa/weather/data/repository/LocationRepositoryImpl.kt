package com.maciekczwa.weather.data.repository

import com.maciekczwa.weather.data.datasource.LocationDataSource
import com.maciekczwa.weather.domain.repository.LocationRepository

class LocationRepositoryImpl(private val locationDataSource: LocationDataSource) : LocationRepository {
    override suspend fun getCurrentLocation() = locationDataSource.getCurrentLocation()
}
