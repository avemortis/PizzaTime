package com.vtorushin.pizzatime

import android.app.Application
import androidx.savedstate.SavedStateRegistryOwner
import com.vtorushin.feature.dishlist.di.MealsListComponent
import com.vtorushin.feature.dishlist.di.MealsListComponentOwner
import com.vtorushin.pizzatime.di.AppComponent
import com.vtorushin.pizzatime.di.DaggerAppComponent

class Application : Application(), MealsListComponentOwner {
    private val appComponent: AppComponent = DaggerAppComponent
        .builder()
        .build()
    private var listComponent: MealsListComponent? = null

    override fun addComponent(savedStateRegistryOwner: SavedStateRegistryOwner): MealsListComponent {
        if (listComponent == null) {
            listComponent = appComponent.listComponent.create(savedStateRegistryOwner)
        }
        return listComponent!!
    }

    override fun clear() {
        listComponent = null
    }
}