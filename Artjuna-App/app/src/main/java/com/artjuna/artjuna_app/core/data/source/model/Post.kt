package com.artjuna.artjuna_app.core.data.source.model

import com.artjuna.artjuna_app.core.data.source.local.entity.PostEntity

data class Post(
    var id:String="",
    var userName:String="",
    var userId:String="",
    var image:String="",
    var productName:String="",
    var like:Int=0,
    var caption:String="",
    var isLiked:Boolean = false
)

fun Post.toPostEntity():PostEntity{
    return PostEntity(
        id=id,
        userName=userName,
        userId=userId,
        image=image,
        productName=productName,
        like=like,
        caption=caption,
        isLiked=isLiked
    )
}