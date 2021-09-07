package com.example.pizzatime.api.model

import com.google.gson.annotations.SerializedName

class CategoryList {
    @SerializedName("meals")
    lateinit var categories: List<Category>

    class Category (
        @SerializedName("strCategory") var category: String = String()
    )
}