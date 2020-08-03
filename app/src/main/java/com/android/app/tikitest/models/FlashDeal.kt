package com.android.app.tikitest.models

import com.google.gson.annotations.SerializedName

data class FlashDeal(
    val status : Int,
    val url : String,
    val tags : String,
    @SerializedName("discount_percent") val discountPercent : Int,
    @SerializedName("special_price") val specialPrice : Long,
    @SerializedName("special_from_date") val specialFromDate : Long,
    @SerializedName("from_date") val fromDate : String,
    @SerializedName("special_to_date") val specialToDate : Long,
    val progress : Progress,
    val product :Product
) {
    data class Progress(
        val qty : Int,
        @SerializedName("qty_ordered") val qtyOrdered : Int,
        @SerializedName("qty_remain") val qtyRemain : Int,
        val percent : Double,
        val status : String
    )

    data class Product(
        val id : Int,
        val sku : String?,
        val name : String,
        @SerializedName("url_path") val urlPath : String,
        val price : Long,
        @SerializedName("list_price") val listPrice : Long,
        val badges : List<String>,
        val discount : Long,
        @SerializedName("rating_average") val ratingAverage : Double,
        @SerializedName("review_count") val reviewCount : Int,
        @SerializedName("order_count") val orderCount : Int,
        @SerializedName("thumbnail_url") val thumbnailUrl : String,
        @SerializedName("is_visible") val isVisible : Boolean,
        @SerializedName("is_fresh") val isFresh : Boolean,
        @SerializedName("is_flower") val isFlower : Boolean,
        @SerializedName("is_gift_card") val isGiftCard : Boolean,
        val inventory : Inventory,
        @SerializedName("url_attendant_input_form") val urlAttendantInputForm : String,
        @SerializedName("master_id")
        val masterId : Long,
        @SerializedName("seller_product_id") val sellerProductId : Long,
        @SerializedName("price_prefix") val pricePrefix : String
    ) {
        data class Inventory (
            @SerializedName("product_virtual_type") val productVirtualType : String?,
            @SerializedName("fulfillment_type") val fulfillmentType : String
        )
    }
}