package com.artjuna.artjuna_app.core.di

import androidx.room.RoomDatabase
import com.artjuna.artjuna_app.core.data.repositories.AuthRepository
import com.artjuna.artjuna_app.core.data.repositories.MainRepository
import com.artjuna.artjuna_app.core.data.source.local.LocalDataSource
import com.artjuna.artjuna_app.core.data.source.local.preferences.Preferences
import com.artjuna.artjuna_app.core.data.source.local.room.ArtjunaDatabase
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
    single { ArtjunaDatabase.getInstance(androidContext()) }
    factory { get<ArtjunaDatabase>().productDao() }
    factory { get<ArtjunaDatabase>().postDao() }
    factory { get<ArtjunaDatabase>().storeDao() }
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
    single { LocalDataSource(get(), get(), get(), get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single { MainRepository(get(),get(),get())}
    single { AuthRepository(get(), get(), get(), get(),get()) }
}
