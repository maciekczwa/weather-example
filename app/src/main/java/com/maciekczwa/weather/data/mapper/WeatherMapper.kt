import com.maciekczwa.weather.domain.model.WeatherEntity

fun WeatherResponse.toEntity() =
    WeatherEntity(
        locationName = name,
        latitude = coord.lat,
        longitude = coord.lon,
        temperature = main.temp,
    )
