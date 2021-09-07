package com.example.pizzatime.model

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.example.pizzatime.api.FoodServices
import com.example.pizzatime.api.model.CategoryList
import com.example.pizzatime.ui.view.MenuRecyclerView

object Menu {
    const val TAG = "Menu"
    val foodService = FoodServices()
    val menu : MutableList<Dish> = mutableListOf()
    lateinit var categoryList: CategoryList
    val listOfCategoryAndIndex : MutableList<MenuRecyclerView.TypeAndIndex> = mutableListOf()
    lateinit var onMenuLoadedListener: OnMenuLoadListener
    var categoryLoadedCount = 0

    fun loadCategories(lifecycleOwner: LifecycleOwner){
        val categories = foodService.getCategories()
        categories.observe(lifecycleOwner,
            {
                categoryList = it
                loadMenu(lifecycleOwner)
            }
        )
    }

    fun loadMenu(lifecycleOwner: LifecycleOwner){
        if (categoryLoadedCount!= categoryList.categories.size){
            val menuLiveData = foodService.getMenuToCategory(categoryList.categories[categoryLoadedCount].category)
            menuLiveData.observe(lifecycleOwner,
                {
                    it.dishes.forEach{ dish ->
                        dish.type = categoryList.categories[categoryLoadedCount].category
                        dish.description = dish.type
                    }
                    listOfCategoryAndIndex.add(MenuRecyclerView.TypeAndIndex(it.dishes[0].type, menu.size))
                    menu.addAll(it.dishes)
                    onMenuLoadedListener.onCategoryLoaded(categoryList.categories[categoryLoadedCount].category)
                    categoryLoadedCount++
                    loadMenu(lifecycleOwner)
                })
        }
        else {
            onMenuLoadedListener.onFullLoad()
        }
    }
    fun printCategories(){
        categoryList.categories.forEach{
            Log.d(TAG, it.category)
        }
    }
}