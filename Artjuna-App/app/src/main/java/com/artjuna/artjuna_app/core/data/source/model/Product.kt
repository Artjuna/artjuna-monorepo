package com.artjuna.artjuna_app.core.data.source.model

import android.os.Parcelable
import com.artjuna.artjuna_app.core.data.source.local.entity.ProductEntity
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadProductRequest
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id:String="",
    var name:String="",
    var image:String="",
    var detail:String="",
    var price:Int=0,
    var category:String="",
    var isCustomizable:Boolean=false,
    var storeId:String="",
    var storeCity:String="",
    var storeName:String="",
):Parcelable

fun Product.toProductRequest():UploadProductRequest{
    return UploadProductRequest(
        ProductName = name,
        Image = image,
        Category = category,
        City = storeCity,
        Caption = detail,
        Price = price
    )
}

fun Product.toProductEntity():ProductEntity{
    return ProductEntity(
        id=id,
        name=name,
        image=image,
        detail=detail,
        price=price,
        category=category,
        storeId=storeId,
        storeCity=storeCity,
        storeName=storeName,
    )
}