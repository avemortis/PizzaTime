package com.example.pizzatime.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pizzatime.api.model.CategoryList
import com.example.pizzatime.api.model.DishList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodServices {
    private val foodApi : FoodApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://www.themealdb.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        foodApi = retrofit.create(FoodApi::class.java)
    }
    fun getCategories() : LiveData<CategoryList>{
        val responseLiveData: MutableLiveData<CategoryList> = MutableLiveData()
        val foodRequest: Call<CategoryList> = foodApi.getCategories()
        var categoriesResponse: CategoryList?

        foodRequest.enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                categoriesResponse = response.body()
                responseLiveData.value = categoriesResponse
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e(TAG, "Failed", t)
            }
        })
        return responseLiveData
    }
    fun getMenuToCategory(category : String) : LiveData<DishList>{
        val responseLiveData: MutableLiveData<DishList> = MutableLiveData()
        val foodRequest: Call<DishList> = foodApi.getMenuByCategory(category)
        var categoriesResponse: DishList?

        foodRequest.enqueue(object : Callback<DishList>{
            override fun onResponse(call: Call<DishList>, response: Response<DishList>) {
                categoriesResponse = response.body()
                responseLiveData.value = categoriesResponse
            }

            override fun onFailure(call: Call<DishList>, t: Throwable) {
                Log.e(TAG, "Failed", t)
            }
        })
        return responseLiveData
    }
    companion object {
        const val TAG = "FoodServices"
    }
}