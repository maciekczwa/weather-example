import android.app.Application
import com.google.android.gms.location.LocationServices
import com.maciekczwa.weather.data.datasource.GoogleLocationDataSource
import com.maciekczwa.weather.data.datasource.LocationDataSource
import org.koin.dsl.module

val frameworkModule =
    module {
        single<LocationDataSource> { GoogleLocationDataSource(get()) }
        single { LocationServices.getFusedLocationProviderClient(get<Application>()) }
    }
