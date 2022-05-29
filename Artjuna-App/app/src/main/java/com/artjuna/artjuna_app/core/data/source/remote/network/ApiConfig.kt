package com.artjuna.artjuna_app.core.data.source.remote.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private val BASE_URL = "http://34.101.203.109:8080/"
    private val BASE_URL_MOCK = "https://61126d5c89c6d00017ac0314.mockapi.io/"

    fun getApiService():ApiService{
        val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit by lazy{
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
        return retrofit.create(ApiService::class.java)
    }

}