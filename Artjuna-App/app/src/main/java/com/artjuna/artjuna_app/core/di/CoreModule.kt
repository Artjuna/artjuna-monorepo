package com.artjuna.artjuna_app.core.di

import com.artjuna.artjuna_app.core.data.repositories.AuthRepository
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.local.LocalDataSource
import com.artjuna.artjuna_app.core.data.source.local.preferences.Preferences
import com.artjuna.artjuna_app.core.data.source.local.room.ProductDatabase
import com.artjuna.artjuna_app.core.data.source.remote.RemoteDataSource
import com.artjuna.artjuna_app.core.data.source.remote.network.ApiConfig
import com.artjuna.artjuna_app.utils.AppExecutors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module{
    single { Preferences(androidContext()) }
}

val databaseModule = module{
    factory { get<ProductDatabase>().productDao() }
    single { ProductDatabase.getInstance(androidContext()) }
}

val networkModule = module{
    single {
        ApiConfig.getApiService()
    }
    single {
        FirebaseAuth.getInstance()
    }
    single {
        Firebase.firestore
    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single { MainRepository(get(),get(),get())}
    single { AuthRepository(get(), get(), get(), get()) }
}
