package com.artjuna.artjuna_app.core.data.source.local

import com.artjuna.artjuna_app.core.data.source.local.entity.ProductEntity
import com.artjuna.artjuna_app.core.data.source.local.preferences.Preferences
import com.artjuna.artjuna_app.core.data.source.local.room.ProductDao
import com.artjuna.artjuna_app.core.data.source.model.Address
import com.artjuna.artjuna_app.core.data.source.model.Product
import com.artjuna.artjuna_app.core.data.source.model.User

class LocalDataSource (
    private val pref:Preferences,
    private val dao:ProductDao
) {

    fun setAddress(address: Address){
        pref.saveAddress(address)
    }

    fun getAddress():Address = pref.getAddress()

    fun saveUser(user: User){
        pref.saveUser(user)
    }

    fun getUser(): User = pref.getUser()

    fun getProductInCartById(id:String) = dao.getProductInCartById(id)

    fun insertProductToCart(productEntity: ProductEntity) = dao.insertProductToCart(productEntity)

    fun deleteProductFromCartById(id:String) = dao.deleteProductInCartById(id)

}