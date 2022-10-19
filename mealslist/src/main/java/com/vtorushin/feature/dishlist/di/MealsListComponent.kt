package com.vtorushin.feature.dishlist.di

import androidx.fragment.app.Fragment
import androidx.savedstate.SavedStateRegistryOwner
import com.vtorushin.feature.dishlist.ViewModelFactory
import com.vtorushin.feature.dishlist.model.api.MealsListService
import com.vtorushin.feature.dishlist.ui.fragments.MealsListFragment
import com.vtorushin.feature.dishlist.ui.fragments.MealsListViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@MealsListScope
@Subcomponent(modules = [MealsListModule::class])
interface MealsListComponent {
    fun inject(fragment: MealsListFragment)
    fun inject(viewModel: MealsListViewModel)
    fun service(): MealsListService
    fun viewModelFactory(): ViewModelFactory.Factory
    fun viewModel(): MealsListViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance savedStateRegistryOwner: SavedStateRegistryOwner
        ): MealsListComponent
    }
}

internal fun Fragment.component() = (requireContext().applicationContext as MealsListComponentOwner)
    .addComponent(this)