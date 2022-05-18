package com.artjuna.artjuna_app.core.di

import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.local.LocalDataSource
import com.artjuna.artjuna_app.core.data.source.local.preferences.Preferences
import com.artjuna.artjuna_app.core.data.source.remote.RemoteDataSource
import com.artjuna.artjuna_app.core.data.source.remote.network.ApiConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module{
    single { Preferences(androidContext()) }
}

val networkModule = module{
    single {
        ApiConfig.getApiService()
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { MainRepository(get(),get()) }
}
