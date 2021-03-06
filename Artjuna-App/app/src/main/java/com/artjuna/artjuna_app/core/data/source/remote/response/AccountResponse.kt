package com.artjuna.artjuna_app.core.data.source.remote.response

import com.artjuna.artjuna_app.core.data.source.model.User
import com.google.gson.annotations.SerializedName

data class AccountResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("Email")
	val email: String,

	@field:SerializedName("UserName")
	val userName: String,

	@field:SerializedName("Telephone")
	val telephone: String,

	@field:SerializedName("UserID")
	val userID: String,

	@field:SerializedName("OriginProvince")
	val originProvince: String,

	@field:SerializedName("FullName")
	val fullName: String,

	@field:SerializedName("OriginCity")
	val originCity: String,

	@field:SerializedName("Followers")
	val followers: Int,

	@field:SerializedName("IsStore")
	val isStore: Boolean


)

fun AccountResponse.toUser():User{
	return User(
		id = userID,
		userName = userName,
		fullName = fullName,
		email = email,
		followers = followers,
		numberWA = telephone.toString(),
		city = originCity,
		province = originProvince,
		isStore = isStore
	)
}