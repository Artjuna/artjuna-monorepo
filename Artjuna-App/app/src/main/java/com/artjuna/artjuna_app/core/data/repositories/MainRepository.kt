package com.artjuna.artjuna_app.core.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.artjuna.artjuna_app.core.data.source.local.LocalDataSource
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.remote.RemoteDataSource
import com.artjuna.artjuna_app.core.data.source.remote.network.Result
import com.artjuna.artjuna_app.utils.DummyData

class MainRepository(private val local:LocalDataSource, private val remote:RemoteDataSource) {

    fun getRecommended(): LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        emit(Result.Success(DummyData.listProduct()))
    }

    fun getRegister():LiveData<Result<Int>> = liveData{
        emit(Result.Loading)
        try {
            remote.getRegister().let {
                if(it.isSuccessful){
                    val body = it.body()
                    emit(Result.Success(body?.size!!))
                }else {
                    emit(Result.Error(it.errorBody().toString() ?: "Default error dongs"))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }
}