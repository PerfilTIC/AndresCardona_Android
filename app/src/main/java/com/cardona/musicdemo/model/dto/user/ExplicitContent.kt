package com.cardona.musicdemo.model.dto.user

import com.google.gson.annotations.SerializedName

data class ExplicitContent(

	@field:SerializedName("filter_locked")
	val filterLocked: Boolean? = null,

	@field:SerializedName("filter_enabled")
	val filterEnabled: Boolean? = null
)