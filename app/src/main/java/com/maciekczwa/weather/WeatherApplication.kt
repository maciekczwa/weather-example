package com.maciekczwa.weather

import android.app.Application
import appModule
import frameworkModule
import networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber
import viewModelModule

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            logger(
                object : Logger() {
                    override fun display(
                        level: Level,
                        msg: MESSAGE,
                    ) {
                        when (level) {
                            Level.DEBUG -> Timber.d(msg)
                            Level.INFO -> Timber.i(msg)
                            Level.WARNING -> Timber.w(msg)
                            Level.ERROR -> Timber.e(msg)
                            Level.NONE -> {}
                        }
                    }
                },
            )
            androidContext(this@WeatherApplication)
            modules(appModule, viewModelModule, networkModule, frameworkModule)
        }
    }
}
