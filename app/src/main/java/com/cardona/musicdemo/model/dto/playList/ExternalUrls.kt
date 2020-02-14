package com.cardona.musicdemo.model.dto.playList

import com.google.gson.annotations.SerializedName

data class ExternalUrls(

	@field:SerializedName("spotify")
	val spotify: String? = null
)