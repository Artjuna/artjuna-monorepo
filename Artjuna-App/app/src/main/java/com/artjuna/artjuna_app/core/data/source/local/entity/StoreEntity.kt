package com.artjuna.artjuna_app.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.core.data.source.model.User
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "storeentities")
data class StoreEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id:String="",

    @ColumnInfo(name = "fullName")
    var fullName:String="",

    @ColumnInfo(name = "userName")
    var userName:String="",

    @ColumnInfo(name = "followers")
    var followers:Int=0,

    @ColumnInfo(name = "numberWA")
    var numberWA:String="",

    @ColumnInfo(name = "city")
    var city:String="",

   @ColumnInfo(name = "province")
    var province:String="",

):Parcelable

fun StoreEntity.toUser():User{
    return User(
        id=id,
        fullName=fullName,
        userName=userName,
        followers=followers,
        numberWA=numberWA,
        city=city,
        province=province,
    )
}


