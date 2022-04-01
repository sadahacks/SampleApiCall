package com.mlkit.sample.apicallsample.application

import android.app.Application
import com.mlkit.sample.apicallsample.di.repositoryModule
import com.mlkit.sample.apicallsample.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }
}