import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maciekczwa.weather.domain.repository.LocationRepository
import com.maciekczwa.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state
        get() = _state.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _state.value = HomeState.Loading
            try {
                val location = locationRepository.getCurrentLocation()
                if (location != null) {
                    _state.value = HomeState.Success(weatherRepository.getWeather(location))
                } else {
                    _state.value = HomeState.NoLocation
                }
            } catch (e: Exception) {
                _state.value = HomeState.Error(e)
            }
        }
    }

    fun locationPermissionDenied() {
        _state.value = HomeState.LocationPermissionDenied
    }
}
