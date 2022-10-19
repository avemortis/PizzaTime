package com.vtorushin.feature.dishlist.di

import androidx.savedstate.SavedStateRegistryOwner
import com.vtorushin.feature.dishlist.ViewModelFactory
import com.vtorushin.feature.dishlist.model.api.MealsListService
import com.vtorushin.feature.dishlist.ui.fragments.MealsListViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class MealsListModule {
    @Provides
    @MealsListScope
    fun provideMealsListService(): MealsListService {
        return Retrofit.Builder()
            .baseUrl("http://www.themealdb.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealsListService::class.java)
    }

    @Provides
    @MealsListScope
    fun provideViewModel(savedStateRegistryOwner: SavedStateRegistryOwner): MealsListViewModel {
        return ViewModelFactory(savedStateRegistryOwner, provideMealsListService()).create(MealsListViewModel::class.java)
    }
}