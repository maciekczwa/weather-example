package com.maciekczwa.weather.domain.repository

import com.maciekczwa.weather.domain.model.LocationEntity

interface LocationRepository {
    suspend fun getCurrentLocation(): LocationEntity?
}
