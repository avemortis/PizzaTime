package com.vtorushin.pizzatime.di

import com.vtorushin.feature.dishlist.di.MealsListComponent
import com.vtorushin.feature.dishlist.di.MealsListScope
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    @MealsListScope
    val listComponent: MealsListComponent.Factory
}