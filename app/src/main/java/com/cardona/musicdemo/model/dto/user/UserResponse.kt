package com.cardona.musicdemo.model.dto.user

import com.cardona.musicdemo.model.dto.playlist.ImageItem
import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("images")
	val images: List<ImageItem?>? = null,

	@field:SerializedName("product")
	val product: String? = null,

	@field:SerializedName("followers")
	val followers: Followers? = null,

	@field:SerializedName("explicit_content")
	val explicitContent: ExplicitContent? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null,

	@field:SerializedName("uri")
	val uri: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)