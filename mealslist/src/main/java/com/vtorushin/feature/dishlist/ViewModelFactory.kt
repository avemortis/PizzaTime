package com.vtorushin.feature.dishlist

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.vtorushin.feature.dishlist.model.api.MealsListService
import com.vtorushin.feature.dishlist.ui.fragments.MealsListViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class ViewModelFactory @AssistedInject constructor(
    @Assisted savedStateRegistryOwner: SavedStateRegistryOwner,
    @Assisted private val service: MealsListService
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return MealsListViewModel(handle, service) as T
    }


    @AssistedFactory
    interface Factory {
        fun create(
            savedStateRegistryOwner: SavedStateRegistryOwner,
            service: MealsListService
        ): ViewModelFactory
    }
}