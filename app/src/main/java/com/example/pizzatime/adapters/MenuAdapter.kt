package com.example.pizzatime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzatime.R
import com.example.pizzatime.model.Dish
import com.squareup.picasso.Picasso

class MenuAdapter(val menu : List<Dish>) : RecyclerView.Adapter<MenuAdapter.MenuHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuHolder(item)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.name.text = menu[position].name
        holder.description.text = menu[position].description
        holder.price.text = "от ${menu[position].price}"
        if (menu[position].imageUrl.isNotEmpty()) Picasso.get().load(menu[position].imageUrl).into(holder.photo)
    }

    override fun getItemCount() = menu.size

    class MenuHolder (item : View) : RecyclerView.ViewHolder(item) {
        var name: TextView = item.findViewById(R.id.menu_item_title)
        var description : TextView = item.findViewById(R.id.menu_item_info)
        var price : Button = item.findViewById(R.id.menu_item_order_button)
        var photo : ImageView = item.findViewById(R.id.menu_item_photo)
    }
}