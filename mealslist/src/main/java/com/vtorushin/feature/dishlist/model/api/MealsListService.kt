package com.vtorushin.feature.dishlist.model.api

import com.vtorushin.core.model.Meals
import com.vtorushin.feature.dishlist.model.Categories
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsListService {
    @GET("api/json/v1/1/list.php?c=list")
    suspend fun getCategories(): Categories

    @GET("api/json/v1/1/filter.php")
    suspend fun getMenuByCategory(
        @Query("c") category: String
    ): Meals
}