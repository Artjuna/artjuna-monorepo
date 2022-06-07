package com.artjuna.artjuna_app.core.data.source.remote.response

import com.artjuna.artjuna_app.core.data.source.model.Post
import com.artjuna.artjuna_app.utils.AppUtils
import com.google.gson.annotations.SerializedName

data class GetPostResponse(

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
	val caption: String,

	@field:SerializedName("FullName")
	val fullName: String?
)

fun GetPostResponse.toPost(): Post {
	return Post(
		id = postID,
		userId = userID,
		image = AppUtils.getPostImageURL(image),
		productName = postName,
		like = like,
		caption = caption,
		userName = fullName ?: userID
	)
}
