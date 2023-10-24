package com.maciekczwa.weather.data.datasource

import android.annotation.SuppressLint
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.maciekczwa.weather.domain.model.LocationEntity
import kotlinx.coroutines.tasks.await

class GoogleLocationDataSource(private val fusedLocationClient: FusedLocationProviderClient) : LocationDataSource {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LocationEntity? {
        // use last location if it is available
        val loc =
            fusedLocationClient
                .lastLocation
                .await()
        return loc?.toDomain()
            // use current location if last location is not available
            ?: fusedLocationClient
                .getCurrentLocation(CurrentLocationRequest.Builder().build(), null).await()
                ?.toDomain()
    }
}

fun android.location.Location.toDomain() = LocationEntity(latitude, longitude)
