package com.example.pizzatime.api

import com.example.pizzatime.api.model.CategoryList
import com.example.pizzatime.api.model.DishList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET(
        "api/json/v1/1/list.php?c=list"
    )
    fun getCategories() : Call<CategoryList>

    @GET(
        "api/json/v1/1/filter.php"
    )
    fun getMenuByCategory(
        @Query ("c") category: String
    ) : Call<DishList>
}