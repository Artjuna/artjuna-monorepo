package com.artjuna.artjuna_app.core.data.source.remote

import com.artjuna.artjuna_app.core.data.source.remote.network.ApiService

class RemoteDataSource(private val api:ApiService) {
    suspend fun getRegister() = api.getRegister()
    suspend fun getProduct() = api.getProduct()
}