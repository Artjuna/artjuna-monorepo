package com.artjuna.artjuna_app.core.data.source.remote

import com.artjuna.artjuna_app.core.data.source.model.Category
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.remote.network.ApiService
import com.artjuna.artjuna_app.core.data.source.remote.request.AddAccountRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UpdateAccountRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadPostRequest
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadProductRequest
import com.artjuna.artjuna_app.core.data.source.remote.response.AccountResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import java.io.File

class RemoteDataSource(private val api:ApiService) {

    //suspend fun getRegister() = api.getRegister()
    suspend fun getProduct() = api.getProduct()
    suspend fun getProductByCategory(category:String) = api.getProductByCategory(category)
    suspend fun uploadProduct(product:Product, image:MultipartBody.Part) =
        api.uploadProduct(
            product.storeId.toRequestBody("text/plain".toMediaType()),
            product.name.toRequestBody("text/plain".toMediaType()),
            product.category.toRequestBody("text/plain".toMediaType()),
            "".toRequestBody("text/plain".toMediaType()),
            product.storeCity.toRequestBody("text/plain".toMediaType()),
            product.detail.toRequestBody("text/plain".toMediaType()),
            product.price,
            image
        )
    suspend fun getCategory() = api.getCategory()
    suspend fun getPost() = api.getPost()
    suspend fun uploadPost(request:UploadPostRequest) = api.uploadPost(request)

    fun addAccount(request:AddAccountRequest) = api.addAccount(request)
    fun getAccountById(userId:String): Call<List<AccountResponse>> = api.getAccountById(userId)
    fun updateAccount(request: UpdateAccountRequest):Call<Void> = api.updateAccount(request)

}