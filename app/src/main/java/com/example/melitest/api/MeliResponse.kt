package com.example.melitest.api

import com.google.gson.annotations.SerializedName

data class MeliResponse(@SerializedName("paging")val paging: MeliPaging,
                        @SerializedName("results") var meliItemResponseList: List<MeliItem>)

data class MeliItem(
    @SerializedName("title") var title: String,
    @SerializedName("price") var price: Double,
    @SerializedName("currency_id") var currency: String,
    @SerializedName("condition") var condition: String,
    @SerializedName("thumbnail") var thumbnail: String,
    @SerializedName("attributes") var attibutes: List<ItemAttributes>
)

data class ItemAttributes(
    @SerializedName("name") var attributeName: String,
    @SerializedName("value_name") var valueAttribute: String
)

data class MeliPaging(@SerializedName("primary_results") val total_offset: Int)