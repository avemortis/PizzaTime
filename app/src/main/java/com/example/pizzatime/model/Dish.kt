package com.example.pizzatime.model

import com.google.gson.annotations.SerializedName

class Dish (
    @SerializedName("strMeal") var name : String = String(),
    @SerializedName("strMealThumb") var imageUrl : String = String(),
    @SerializedName("idMeal") var id : String = String(),
    var type : String = String(),
    var description : String = "Description here",
    val price : Int = 500,
)