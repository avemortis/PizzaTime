package com.example.pizzatime.model

interface OnMenuLoadListener {
    fun onCategoryLoaded(category : String)
    fun onFullLoad()
}