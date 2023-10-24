import com.maciekczwa.weather.BuildConfig
import com.maciekczwa.weather.data.repository.LocationRepositoryImpl
import com.maciekczwa.weather.data.repository.WeatherRepositoryImpl
import com.maciekczwa.weather.domain.repository.LocationRepository
import com.maciekczwa.weather.domain.repository.WeatherRepository
import org.koin.dsl.module

val appModule =
    module {
        single<WeatherRepository> { WeatherRepositoryImpl(get(), BuildConfig.OPENWEATHER_API_KEY) }
        single<LocationRepository> { LocationRepositoryImpl(get()) }
    }
