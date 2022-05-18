package com.artjuna.artjuna_app.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("RegisterResponse")
	val registerResponse: List<RegisterResponseItem>
)

data class RegisterResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("isLogged")
	val isLogged: Boolean,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String,

	@field:SerializedName("username")
	val username: String
)
