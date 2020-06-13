package com.route.islami.ApiManager.model

import com.google.gson.annotations.SerializedName

data class RadioResponse(

	@field:SerializedName("radios")
	val radios: List<RadioChannel?>? = null
)

data class RadioChannel(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("radio_url")
	val radioUrl: String? = null
)
