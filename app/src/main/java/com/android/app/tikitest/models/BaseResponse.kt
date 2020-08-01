package com.android.app.tikitest.models

import com.google.gson.annotations.SerializedName

data class BaseResponse<T : Any> (
    val data : T?,
    val tabs : List<Tab>?
) {
    data class Tab(
        @SerializedName("query_value") val queryValue : Int,
        @SerializedName("from_date") val fromDate : String,
        @SerializedName("to_date") val toDate : String,
        val display : String,
        val active : Boolean
    )
}