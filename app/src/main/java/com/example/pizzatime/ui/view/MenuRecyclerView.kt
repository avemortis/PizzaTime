package com.example.pizzatime.ui.view

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzatime.adapters.MenuAdapter
import com.example.pizzatime.model.Menu

class MenuRecyclerView (val recyclerView: RecyclerView, val onNewTypeOfMenuScrolledListener: OnNewTypeOfMenuScrolledListener) {
    lateinit var adapter : MenuAdapter
    val listOfTypesAndIndexes : MutableList<TypeAndIndex> = mutableListOf()

    var activeType : String = String()
    var activeTypeIndex : Int = 0

    fun prepare(){
        val layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = layoutManager
        adapter = MenuAdapter(Menu.menu)
        recyclerView.adapter = adapter
        listOfTypesAndIndexes.addAll(Menu.listOfCategoryAndIndex)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) {
                    if (layoutManager.findFirstCompletelyVisibleItemPosition() != -1){
                        if (Menu.menu[layoutManager.findFirstCompletelyVisibleItemPosition()].type != activeType) {
                            activeTypeIndex--
                            activeType = Menu.menu[layoutManager.findFirstCompletelyVisibleItemPosition()].type
                            onNewTypeOfMenuScrolledListener.onNewTypeScrolled(activeTypeIndex)

                        }
                    }
                }
                if (dy > 0) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() != -1){
                        if (Menu.menu[layoutManager.findLastCompletelyVisibleItemPosition()].type !=activeType) {
                            activeTypeIndex++
                            activeType = Menu.menu[layoutManager.findLastCompletelyVisibleItemPosition()].type
                            onNewTypeOfMenuScrolledListener.onNewTypeScrolled(activeTypeIndex)

                        }
                    }
                }
            }
        })
    }

    fun scrollToSelectTypeIndex(index : Int){
        activeTypeIndex = index
        activeType = listOfTypesAndIndexes[index].type
        recyclerView.scrollToPosition(listOfTypesAndIndexes[index].index)
    }

    interface OnNewTypeOfMenuScrolledListener{
        fun onNewTypeScrolled(index: Int)
    }

    class TypeAndIndex(val type : String, val index : Int)

    companion object {
        const val TAG = "MenuRecyclerView"
    }
}