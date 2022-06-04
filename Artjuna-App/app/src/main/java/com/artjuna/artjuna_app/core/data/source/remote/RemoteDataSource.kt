package com.artjuna.artjuna_app.core.data.source.remote

import com.artjuna.artjuna_app.core.data.source.model.Category
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.remote.network.ApiService
import com.artjuna.artjuna_app.core.data.source.remote.request.*
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
    suspend fun getProductByName(name:String) = api.getProductByName(name)

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

    suspend fun uploadPost(post: Post,image: MultipartBody.Part) =
        api.uploadPost(
            post.userId.toRequestBody("text/plain".toMediaType()),
            post.productName.toRequestBody("text/plain".toMediaType()),
            post.caption.toRequestBody("text/plain".toMediaType()),
            image
        )

    suspend fun addHasSeen(request:AddHasSeenRequest) = api.addHasSeen(request)


    fun addAccount(request:AddAccountRequest) = api.addAccount(request)
    fun getAccountById(userId:String): Call<List<AccountResponse>> = api.getAccountById(userId)
    fun updateAccount(request: UpdateAccountRequest):Call<Void> = api.updateAccount(request)

}