package com.artjuna.artjuna_app.core.data.source.remote.response

import com.artjuna.artjuna_app.core.data.source.model.Product
import com.google.gson.annotations.SerializedName

data class GetProductResponse(

	@field:SerializedName("store_id")
	val storeId: String,

	@field:SerializedName("storeImage")
	val storeImage: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("sold")
	val sold: Int,

	@field:SerializedName("store_city")
	val storeCity: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Int,

	@field:SerializedName("store_name")
	val storeName: String,

	@field:SerializedName("detail")
	val detail: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("rating_amount")
	val ratingAmount: Int
)

fun GetProductResponse.toProduct():Product{
	return Product(
		id = id,
		name = name,
		image = image,
		detail = detail,
		price = price,
		rating = rating.toDouble(),
		ratingAmount = ratingAmount.toDouble(),
		sold = sold,
		storeId = storeId,
		storeCity = storeCity,
		storeName = storeName,
		storeImage = storeImage,


	)
}
