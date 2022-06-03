package com.artjuna.artjuna_app.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GetCategoryResponse(

	@field:SerializedName("Category")
	val category: String
)
