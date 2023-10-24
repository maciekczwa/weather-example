import com.maciekczwa.weather.domain.model.WeatherEntity

sealed class HomeState {
    object Loading : HomeState()

    object LocationPermissionDenied : HomeState()

    object NoLocation : HomeState()

    data class Error(val error: Throwable) : HomeState()

    data class Success(val weather: WeatherEntity) : HomeState()
}
