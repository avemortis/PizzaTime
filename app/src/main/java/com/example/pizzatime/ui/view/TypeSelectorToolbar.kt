package com.example.pizzatime.ui.view

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.pizzatime.R
import java.lang.IllegalStateException

class TypeSelectorToolbar (
    val toolbar : Toolbar,
    val onTypeListener : OnTypeListener
    ) : View.OnClickListener {
    val horizontalScrollView : HorizontalScrollView = HorizontalScrollView(toolbar.context)

    val linearLayout : LinearLayout = LinearLayout(toolbar.context)

    var prepared = false

    val buttons : MutableList<Button> = mutableListOf()

    fun prepare(){
        linearLayout.orientation = LinearLayout.HORIZONTAL
        toolbar.addView(horizontalScrollView)
        horizontalScrollView.addView(linearLayout)
        prepared = true
    }

    fun addType(type : String, margin : Int = 8){
        if (prepared == false){
            prepare()
        }
        val params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.marginEnd = margin
        val button: Button = Button(ContextThemeWrapper(toolbar.context, R.style.Theme_PizzaTime_TypeSelectorButton), null, 0)
        button.text = type
        button.id = buttons.size
        button.setOnClickListener(this)
        buttons.add(button)
        linearLayout.addView(button, params)
    }

    fun setUnselect(type : String){
        buttons.forEach{
            if (it.text == type){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    it.background.colorFilter = BlendModeColorFilter((ContextCompat.getColor(toolbar.context, R.color.white)), BlendMode.MULTIPLY)
                }
                else it.background.setColorFilter(ContextCompat.getColor(toolbar.context, R.color.white), PorterDuff.Mode.MULTIPLY)
                it.setTextColor(ContextCompat.getColor(toolbar.context, R.color.unactive))
            }
        }
    }

    fun setUnselect(number : Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            buttons[number].background.colorFilter = BlendModeColorFilter((ContextCompat.getColor(toolbar.context, R.color.white)), BlendMode.MULTIPLY)
        }
        else buttons[number].background.setColorFilter(ContextCompat.getColor(toolbar.context, R.color.white), PorterDuff.Mode.MULTIPLY)
        buttons[number].setTextColor(ContextCompat.getColor(toolbar.context, R.color.unactive))
    }

    fun setUnselectAll(){
        buttons.forEach{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    it.background.colorFilter = BlendModeColorFilter((ContextCompat.getColor(toolbar.context, R.color.white)), BlendMode.MULTIPLY)
                }
                else it.background.setColorFilter(ContextCompat.getColor(toolbar.context, R.color.white), PorterDuff.Mode.MULTIPLY)
                it.setTextColor(ContextCompat.getColor(toolbar.context, R.color.unactive))
        }
    }

    fun setSelect(type : String){
        buttons.forEach{
            if (it.text == type){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    it.background.colorFilter = BlendModeColorFilter((ContextCompat.getColor(toolbar.context, R.color.active_20)), BlendMode.MULTIPLY)
                }
                else it.background.setColorFilter(ContextCompat.getColor(toolbar.context, R.color.active_20), PorterDuff.Mode.MULTIPLY)
                it.setTextColor(ContextCompat.getColor(toolbar.context, R.color.active))
            }
        }
    }

    fun setSelect(number : Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            buttons[number].background.colorFilter = BlendModeColorFilter((ContextCompat.getColor(toolbar.context, R.color.active_20)), BlendMode.MULTIPLY)
        }
        else buttons[number].background.setColorFilter(ContextCompat.getColor(toolbar.context, R.color.active_20), PorterDuff.Mode.MULTIPLY)
        buttons[number].setTextColor(ContextCompat.getColor(toolbar.context, R.color.active))
    }


    fun setSelectAll(){
        buttons.forEach{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                it.background.colorFilter = BlendModeColorFilter((ContextCompat.getColor(toolbar.context, R.color.active_20)), BlendMode.MULTIPLY)
            }
            else it.background.setColorFilter(ContextCompat.getColor(toolbar.context, R.color.active_20), PorterDuff.Mode.MULTIPLY)
            it.setTextColor(ContextCompat.getColor(toolbar.context, R.color.active))
        }
    }

    override fun onClick(v: View?) {
        var count = 0
        buttons.forEach{
            if (v?.id == it.id){
                onTypeListener.onTypeClick(it.text.toString())
                onTypeListener.onTypeClick(count)
            }
            count++
        }
    }

    interface OnTypeListener{
        fun onTypeClick(type : String)
        fun onTypeClick(number : Int)
    }
}