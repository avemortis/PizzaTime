package com.vtorushin.feature.dishlist.ui.fragments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtorushin.core.model.Meal
import com.vtorushin.core.resources.Resource
import com.vtorushin.core.resources.Status
import com.vtorushin.feature.dishlist.model.Categories
import com.vtorushin.feature.dishlist.model.api.MealsListService
import com.vtorushin.feature.dishlist.utils.combineMealsIntoSingleList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealsListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val service: MealsListService
) : ViewModel() {
    private val _categories: MutableStateFlow<Resource<Categories>> =
        MutableStateFlow(Resource.loading())
    val categories = _categories.asStateFlow().apply {
        loadCategories()
    }

    private val _menu: MutableStateFlow<List<Meal>> = MutableStateFlow(emptyList())
    val menu = _menu.asStateFlow()

    val categoryMenuMap: MutableMap<String, List<Meal>> =
        mutableMapOf()

    var activeCategory = 0

    private fun loadCategories() {
        if (_categories.value.status != Status.COMPLETE)
            viewModelScope.launch {
                try {
                    val categories = service.getCategories()
                    _categories.emit(Resource.complete(categories))
                    categories.categories.forEach {
                        categoryMenuMap[it.strCategory] = emptyList()
                    }
                    loadMenu()
                } catch (e: Exception) {
                    _categories.emit(Resource.error(e.message ?: "Error"))
                }
            }
    }

    private fun loadMenu() {
        if (menu.value.isEmpty()) {
            viewModelScope.launch {
                val menuSize = categories.value.data?.categories?.size ?: 0
                repeat(menuSize) { count ->
                    viewModelScope.launch {
                        val category = categories.value.data?.categories?.get(count)?.strCategory
                        if (category != null) {
                            val menuForCategory = service.getMenuByCategory(category)
                            categoryMenuMap[category] = menuForCategory.meals
                            if (count == menuSize - 1) {
                                _menu.emit(categoryMenuMap.combineMealsIntoSingleList())
                            }
                        }
                    }
                }
            }
        }
    }
}