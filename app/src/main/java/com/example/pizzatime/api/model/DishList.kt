package com.example.pizzatime.api.model

import com.example.pizzatime.model.Dish
import com.google.gson.annotations.SerializedName

class DishList {
    @SerializedName ("meals")
    lateinit var dishes : List<Dish>
}