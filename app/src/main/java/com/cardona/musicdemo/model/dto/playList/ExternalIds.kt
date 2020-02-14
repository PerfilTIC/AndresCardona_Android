package com.cardona.musicdemo.model.dto.playList

import com.google.gson.annotations.SerializedName

data class ExternalIds(

	@field:SerializedName("isrc")
	val isrc: String? = null
)