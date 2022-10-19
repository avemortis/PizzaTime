package com.vtorushin.core.model

import com.google.gson.annotations.SerializedName

data class Meals(
    @SerializedName("meals")
    val meals: List<Meal>
)

data class Meal(
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String,
    @SerializedName("idMeal")
    val id: String
)