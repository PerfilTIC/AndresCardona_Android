package com.cardona.musicdemo.model.dto.playList

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@field:SerializedName("video_thumbnail")
	val videoThumbnail: VideoThumbnail? = null,

	@field:SerializedName("added_at")
	val addedAt: String? = null,

	@field:SerializedName("added_by")
	val addedBy: AddedBy? = null,

	@field:SerializedName("is_local")
	val isLocal: Boolean? = null,

	@field:SerializedName("primary_color")
	val primaryColor: Any? = null,

	@field:SerializedName("track")
	val track: Track? = null

)