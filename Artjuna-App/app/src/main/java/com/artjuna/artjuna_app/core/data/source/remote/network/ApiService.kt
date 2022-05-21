package com.artjuna.artjuna_app.core.data.source.remote.network

import com.artjuna.artjuna_app.core.data.source.remote.response.GetPostResponse
import com.artjuna.artjuna_app.core.data.source.remote.response.GetProductResponse
import com.artjuna.artjuna_app.core.data.source.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("register")
    suspend fun getRegister():Response<List<RegisterResponse>>

    @GET("product")
    suspend fun getProduct():Response<List<GetProductResponse>>

    @GET("post")
    suspend fun getPost():Response<List<GetPostResponse>>


}