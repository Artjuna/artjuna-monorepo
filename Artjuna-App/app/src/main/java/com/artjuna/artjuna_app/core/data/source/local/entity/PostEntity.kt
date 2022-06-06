package com.artjuna.artjuna_app.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artjuna.artjuna_app.core.data.source.model.Post
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "posttentities")
data class PostEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id:String="",

    @ColumnInfo(name = "userName")
    var userName:String="",

    @ColumnInfo(name = "userId")
    var userId:String="",

    @ColumnInfo(name = "image")
    var image:String="",

    @ColumnInfo(name = "productName")
    var productName:String="",

    @ColumnInfo(name = "like")
    var like:Int=0,

    @ColumnInfo(name = "caption")
    var caption:String="",

    @ColumnInfo(name = "isLiked")
    var isLiked:Boolean=false,


):Parcelable

fun PostEntity.toPost():Post{
    return Post(
        id=id,
        userName=userName,
        userId=userId,
        image=image,
        productName=productName,
        like=like,
        caption=caption,
        isLiked = isLiked
    )
}
