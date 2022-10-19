package com.vtorushin.feature.dishlist.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vtorushin.feature.dishlist.databinding.ViewholderCategoryActiveBinding


class CategoriesAdapter(
    private val categories: List<String>,
    private var activeCategoryNumber: Int = 0,
    private val onCategoryClick: (categoryNumber: Int) -> Unit
): RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ViewholderCategoryActiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.button.textOff = categories[position]
        holder.button.textOn = categories[position]
        holder.button.setOnClickListener {
            setActiveCategory(position)
            onCategoryClick(position)
        }
        holder.button.isChecked = position == activeCategoryNumber
    }

    override fun getItemCount() = categories.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun setActiveCategory(categoryNumber: Int) {
        val old = activeCategoryNumber
        activeCategoryNumber = categoryNumber
        notifyItemChanged(old)
        notifyItemChanged(activeCategoryNumber)
    }
}

class CategoryViewHolder(binding: ViewholderCategoryActiveBinding) : RecyclerView.ViewHolder(binding.root) {
    val button: ToggleButton = binding.button
}


