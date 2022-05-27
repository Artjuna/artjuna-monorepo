package com.artjuna.artjuna_app.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GetAllPostResponse(

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
