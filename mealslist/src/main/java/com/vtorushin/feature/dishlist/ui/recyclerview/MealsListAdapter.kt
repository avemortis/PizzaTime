package com.vtorushin.feature.dishlist.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vtorushin.feature.dishlist.databinding.ViewholderMealBinding

class MealsListAdapter(
    onNewSegmentReached: (segmentNumber: Int) -> Unit,
    val onBind: (holder: MealViewHolder, position: Int) -> Unit,
    override var segmentsEndsPositionsList: List<Int>
) : SegmentListenableAdapter<MealViewHolder>(onNewSegmentReached) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding =
            ViewholderMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        if (segmentsEndsPositionsList.isNotEmpty())
            onBind(holder, position)
    }

    override fun getItemCount() =
        if (segmentsEndsPositionsList.isNotEmpty()) segmentsEndsPositionsList.last() else 0
}

class MealViewHolder(binding: ViewholderMealBinding) : RecyclerView.ViewHolder(binding.root) {
    val picture = binding.mealPicture
    val title = binding.mealTitle
    val composition = binding.mealComposition
}