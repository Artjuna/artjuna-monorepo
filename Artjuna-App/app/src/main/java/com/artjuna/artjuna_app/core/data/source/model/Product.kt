package com.artjuna.artjuna_app.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id:String="",
    var name:String="",
    var image:String="",
    var detail:String="",
    var price:Int=0,
    var rating:Double=0.0,
    var ratingAmount:Double=0.0,
    var sold:Int=0,
    var storeId:String="",
    var storeCity:String="",
    var storeName:String="",
    var storeImage:String="",
):Parcelable
