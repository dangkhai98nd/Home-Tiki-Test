package com.android.app.tikitest.models

import com.google.gson.annotations.SerializedName

data class Banner(
    val id : Int,
    val title : String,
    val content : String,
    val url : String,
    @SerializedName("image_url") val imageUrl : String,
    @SerializedName("thumbnail_url") val thumbnailUrl : String,
    @SerializedName("mobile_url") val mobileUrl : String,
    val ratio : String
)