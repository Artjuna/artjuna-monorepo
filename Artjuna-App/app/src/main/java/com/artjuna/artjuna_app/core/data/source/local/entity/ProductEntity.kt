package com.artjuna.artjuna_app.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artjuna.artjuna_app.core.data.source.model.Product
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "productentities")
data class ProductEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id:String="",

    @ColumnInfo(name = "name")
    var name:String="",

    @ColumnInfo(name = "image")
    var image:String="",

    @ColumnInfo(name = "detail")
    var detail:String="",

    @ColumnInfo(name = "price")
    var price:Int=0,

    @ColumnInfo(name = "category")
    var category:String="",

    @ColumnInfo(name = "storeId")
    var storeId:String="",

    @ColumnInfo(name = "storeCity")
    var storeCity:String="",

    @ColumnInfo(name = "storeProvince")
    var storeProvince:String="",

    @ColumnInfo(name = "storeName")
    var storeName:String="",



):Parcelable

fun ProductEntity.toProduct():Product{
    return Product(
        id=id,
        name=name,
        image=image,
        detail=detail,
        price=price,
        category=category,
        storeId=storeId,
        storeCity=storeCity,
        storeProvince=storeProvince,
        storeName=storeName,
    )
}
