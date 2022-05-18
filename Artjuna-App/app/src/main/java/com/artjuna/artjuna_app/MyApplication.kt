package com.artjuna.artjuna_app

import android.app.Application
import com.artjuna.artjuna_app.core.di.networkModule
import com.artjuna.artjuna_app.core.di.preferencesModule
import com.artjuna.artjuna_app.core.di.repositoryModule
import com.artjuna.artjuna_app.core.di.viewModelModule
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
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}