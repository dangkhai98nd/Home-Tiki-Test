package com.android.app.tikitest.models

import com.google.gson.annotations.SerializedName

data class QuickLink(
    val title : String,
    val content : String,
    val url : String,
    val authentication : String,
    @SerializedName("web_url") val webUrl : String,
    @SerializedName("image_url") val imageUrl : String
)