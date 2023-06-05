package com.bangkit.haze.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(

	@field:SerializedName("probability")
	val probability: String? = null,

	@field:SerializedName("label")
	val label: String? = null
)
