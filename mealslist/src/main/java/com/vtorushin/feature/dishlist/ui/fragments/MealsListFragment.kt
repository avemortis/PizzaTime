package com.vtorushin.feature.dishlist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.vtorushin.core.resources.Status
import com.vtorushin.feature.dishlist.databinding.FragmentMenuMealsBinding
import com.vtorushin.feature.dishlist.di.component
import com.vtorushin.feature.dishlist.ui.recyclerview.CategoriesAdapter
import com.vtorushin.feature.dishlist.ui.recyclerview.MealsListAdapter
import com.vtorushin.feature.dishlist.utils.findStartOfTheSegment
import com.vtorushin.feature.dishlist.utils.toSegmentList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MealsListFragment : Fragment() {
    private lateinit var viewModel: MealsListViewModel
    private lateinit var binding: FragmentMenuMealsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = component().viewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuMealsBinding.inflate(inflater, container, false)
        binding.scrollContent.list.adapter = getMealsListAdapter()
        collectCategories()
        collectMenu()
        return binding.root
    }

    private fun collectCategories() {
        lifecycleScope.launch {
            viewModel.categories.collectLatest { res ->
                when (res.status) {
                    Status.LOADING -> {}
                    Status.COMPLETE -> {
                        binding.categories.adapter =
                            getCategoriesAdapter(res.data?.categories?.map { it.strCategory }
                                ?: emptyList())
                    }
                    Status.ERROR -> {}
                }
            }
        }
    }

    private fun getMealsListAdapter() = run {
        MealsListAdapter(
            onNewSegmentReached = { segmentNumber ->
                (binding.categories.adapter as CategoriesAdapter).setActiveCategory(
                    segmentNumber
                )
                binding.categories.smoothScrollToPosition(segmentNumber)
                viewModel.activeCategory = segmentNumber
            },
            onBind = { holder, position ->
                val meal = viewModel.menu.value[position]
                holder.title.text = meal.strMeal
                Glide.with(requireContext())
                    .load(meal.strMealThumb)
                    .into(holder.picture)
            },
            segmentsEndsPositionsList = emptyList()
        )
    }

    private fun collectMenu() {
        val adapter = binding.scrollContent.list.adapter as MealsListAdapter
        lifecycleScope.launch {
            viewModel.menu.collect { list ->
                adapter.segmentsEndsPositionsList = viewModel.categoryMenuMap.toSegmentList()
                adapter.notifyItemRangeInserted(0, list.size)
            }
        }
    }

    private fun getCategoriesAdapter(categories: List<String>) = run {
        CategoriesAdapter(
            categories = categories,
            activeCategoryNumber = viewModel.activeCategory,
            onCategoryClick = { categoryNumber ->
                val number = viewModel.categoryMenuMap.findStartOfTheSegment(categoryNumber)
                viewModel.activeCategory = categoryNumber
                binding.scrollContent.list.scrollToPosition(number)
            })
    }
}