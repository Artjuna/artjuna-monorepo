package com.artjuna.artjuna_app.core.data.source.remote.response

import com.artjuna.artjuna_app.core.data.source.model.Post
import com.google.gson.annotations.SerializedName

data class GetPostResponse(

	@field:SerializedName("storeImage")
	val storeImage: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("productId")
	val productId: String,

	@field:SerializedName("caption")
	val caption: String,

	@field:SerializedName("storeName")
	val storeName: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("productName")
	val productName: String,

	@field:SerializedName("likes")
	val likes: Int
)

fun GetPostResponse.toPost(): Post {
	return Post(
		id = id,
		userName = storeName,
		userImage = storeImage,
		image = image,
		productName = productName,
		productId = productId,
		like = likes,
		caption = caption,
	)
}
