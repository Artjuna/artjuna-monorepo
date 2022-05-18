package com.artjuna.artjuna_app.core.data.source.remote.network

import com.artjuna.artjuna_app.core.data.source.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("register")
    suspend fun getRegister():Response<List<RegisterResponse>>
}