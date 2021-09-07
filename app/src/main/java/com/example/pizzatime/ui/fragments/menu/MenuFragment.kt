package com.example.pizzatime.ui.fragments.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pizzatime.R
import com.example.pizzatime.adapters.MenuAdapter
import com.example.pizzatime.databinding.FragmentMenuBinding
import com.example.pizzatime.model.Dish
import com.example.pizzatime.model.Menu
import com.example.pizzatime.model.OnMenuLoadListener
import com.example.pizzatime.ui.view.MenuRecyclerView
import com.example.pizzatime.ui.view.TypeSelectorToolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

class MenuFragment : Fragment() , TypeSelectorToolbar.OnTypeListener , MenuRecyclerView.OnNewTypeOfMenuScrolledListener, OnMenuLoadListener {

    private lateinit var menuViewModel: MenuViewModel
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var typeSelectorToolbar: TypeSelectorToolbar
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var menuRecyclerView : MenuRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuViewModel =
            ViewModelProvider(this).get(MenuViewModel::class.java)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        appBarLayout = binding.appBar
        val toolbar = binding.typeBar

        typeSelectorToolbar = TypeSelectorToolbar(toolbar, this)
        menuRecyclerView = MenuRecyclerView(root.findViewById(R.id.menu_recyclerview), this)
        Menu.onMenuLoadedListener = this

        typeSelectorToolbar.prepare()

        if (Menu.menu.isNotEmpty()){
            Menu.categoryList.categories.forEach{
                typeSelectorToolbar.addType(it.category)
            }
            menuRecyclerView.prepare()
            menuRecyclerView.activeTypeIndex = menuViewModel.activeType
            menuRecyclerView.activeType = Menu.menu[menuViewModel.activeType].type
            typeSelectorToolbar.setUnselectAll()
            typeSelectorToolbar.setSelect(menuViewModel.activeType)
            menuRecyclerView.scrollToSelectTypeIndex(menuViewModel.activeType)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Menu.menu.isEmpty()){
            Menu.loadCategories(this.viewLifecycleOwner)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTypeClick(type: String) {
    }

    override fun onTypeClick(number: Int) {
        typeSelectorToolbar.setUnselect(menuViewModel.activeType)
        typeSelectorToolbar.setSelect(number)
        menuViewModel.activeType = number
        appBarLayout.setExpanded(false)
        menuRecyclerView.scrollToSelectTypeIndex(number)
    }

    override fun onNewTypeScrolled(index: Int) {
        typeSelectorToolbar.setUnselect(menuViewModel.activeType)
        menuViewModel.activeType = index
        typeSelectorToolbar.setSelect(index)
    }
    companion object {
        const val TAG = "MenuFragment"
    }

    override fun onCategoryLoaded(category: String) {
        typeSelectorToolbar.addType(category)
    }

    override fun onFullLoad() {
        menuRecyclerView.prepare()
        menuRecyclerView.adapter.notifyDataSetChanged()
        menuRecyclerView.activeType = Menu.menu[0].type
        typeSelectorToolbar.setUnselectAll()
        typeSelectorToolbar.setSelect(0)
    }
}