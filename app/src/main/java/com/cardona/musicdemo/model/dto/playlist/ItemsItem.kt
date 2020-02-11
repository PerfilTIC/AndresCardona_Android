package com.cardona.musicdemo.model.dto.playlist

import com.google.gson.annotations.SerializedName

data class ItemsItem(

    @field:SerializedName("owner")
	val owner: Owner? = null,

    @field:SerializedName("images")
	val images: List<ImageItem?>? = null,

    @field:SerializedName("public")
	val jsonMemberPublic: Boolean? = null,

    @field:SerializedName("snapshot_id")
	val snapshotId: String? = null,

    @field:SerializedName("collaborative")
	val collaborative: Boolean? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("href")
	val href: String? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("type")
	val type: String? = null,

    @field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null,

    @field:SerializedName("uri")
	val uri: String? = null,

    @field:SerializedName("tracks")
	val tracks: Tracks? = null
)