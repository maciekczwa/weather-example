import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.maciekczwa.weather.data.network.WeatherApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber

val networkModule =
    module {
        single<WeatherApi> { get<Retrofit>().create(WeatherApi::class.java) }
        single<Retrofit> {
            Retrofit.Builder().client(get()).baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType())).build()
        }

        single<Json> {
            Json { ignoreUnknownKeys = true }
        }

        single {
            OkHttpClient.Builder()
                .addNetworkInterceptor(
                    HttpLoggingInterceptor {
                        Timber.d(it)
                    }.apply { setLevel(HttpLoggingInterceptor.Level.BODY) },
                )
                .build()
        }
    }
