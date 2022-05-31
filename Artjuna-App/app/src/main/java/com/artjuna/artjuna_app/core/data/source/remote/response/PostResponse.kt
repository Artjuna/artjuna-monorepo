package com.artjuna.artjuna_app.core.data.source.remote.response

import com.artjuna.artjuna_app.core.data.source.model.Post
import com.google.gson.annotations.SerializedName

data class PostResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("Category")
	val category: String,

	@field:SerializedName("Like")
	val like: Int,

	@field:SerializedName("UserID")
	val userID: String,

	@field:SerializedName("Image")
	val image: String,

	@field:SerializedName("PostName")
	val postName: String,

	@field:SerializedName("PostID")
	val postID: String,

	@field:SerializedName("Caption")
	val caption: String
)

fun PostResponse.toPost():Post{
	return Post(
		id = postID,
		userId = userID,
		image = image,
		caption = caption,
		productName = postName,
		like = like
	)
}
