package com.maciekczwa.weather.data.datasource

import com.maciekczwa.weather.domain.model.LocationEntity

interface LocationDataSource {
    suspend fun getCurrentLocation(): LocationEntity?
}
