package com.cardona.musicdemo.model.dto.myLists

import com.google.gson.annotations.SerializedName

data class MyListsResponse(

	@field:SerializedName("next")
	val next: Any? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)