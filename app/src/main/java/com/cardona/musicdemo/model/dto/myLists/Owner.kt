package com.cardona.musicdemo.model.dto.myLists

import com.google.gson.annotations.SerializedName

data class Owner(

    @field:SerializedName("href")
	val href: String? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("type")
	val type: String? = null,

    @field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null,

    @field:SerializedName("uri")
	val uri: String? = null
)