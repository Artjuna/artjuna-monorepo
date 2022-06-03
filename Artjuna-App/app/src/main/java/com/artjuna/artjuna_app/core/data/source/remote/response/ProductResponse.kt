package com.artjuna.artjuna_app.core.data.source.remote.response

import com.artjuna.artjuna_app.core.data.source.model.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("Category")
	val category: String,

	@field:SerializedName("Description")
	val description: String,

	@field:SerializedName("ProductName")
	val productName: String,

	@field:SerializedName("Price")
	val price: Int,

	@field:SerializedName("UserID")
	val userID: String,

	@field:SerializedName("HasSeen")
	val hasSeen: Int?,

	@field:SerializedName("ProductID")
	val productID: String,

	@field:SerializedName("City")
	val city: String,

	@field:SerializedName("Image")
	val image: String,

	@field:SerializedName("Province")
	val province: String
)

fun ProductResponse.toProduct():Product{
	return Product(
		id = productID,
		name = productName,
		image = image,
		detail = description,
		price = price,
		category = category,
		storeId = userID,
		storeCity = city,
	)
}