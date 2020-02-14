package com.cardona.musicdemo.model.dto.myLists

import com.google.gson.annotations.SerializedName

data class ImageItem(

    @field:SerializedName("height")
    val height: Int? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("width")
    val width: Int? = null
)