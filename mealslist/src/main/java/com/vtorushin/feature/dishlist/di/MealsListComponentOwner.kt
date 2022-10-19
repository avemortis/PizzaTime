package com.vtorushin.feature.dishlist.di

import androidx.savedstate.SavedStateRegistryOwner

interface MealsListComponentOwner {
    fun addComponent(savedStateRegistryOwner: SavedStateRegistryOwner): MealsListComponent
    fun clear()
}