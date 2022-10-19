package com.vtorushin.feature.dishlist.model

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("meals")
    val categories: List<Category>
)

data class Category(
    @SerializedName("strCategory")
    val strCategory: String
)