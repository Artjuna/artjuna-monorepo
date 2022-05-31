package com.artjuna.artjuna_app.core.data.source.model

import android.os.Parcelable
import com.artjuna.artjuna_app.core.data.source.remote.request.UploadProductRequest
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id:String="",
    var name:String="",
    var image:String="",
    var detail:String="",
    var price:Int=0,
    var rating:Double=0.0,
    var category:String="",
    var isCustomizable:Boolean=false,
    var ratingAmount:Double=0.0,
    var sold:Int=0,
    var storeId:String="",
    var storeCity:String="",
    var storeName:String="",
    var storeImage:String="",
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