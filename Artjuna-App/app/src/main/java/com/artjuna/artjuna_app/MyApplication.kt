package com.artjuna.artjuna_app

import android.app.Application
import com.artjuna.artjuna_app.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    preferencesModule,
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}