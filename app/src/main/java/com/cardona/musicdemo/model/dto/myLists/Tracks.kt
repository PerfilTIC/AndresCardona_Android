package com.cardona.musicdemo.model.dto.myLists

import com.google.gson.annotations.SerializedName

data class Tracks(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("href")
	val href: String? = null
)