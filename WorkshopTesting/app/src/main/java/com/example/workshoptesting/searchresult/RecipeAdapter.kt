package com.example.workshoptesting.searchresult

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.workshoptesting.R
import com.example.workshoptesting.api.Food
import kotlinx.android.synthetic.main.list_item.view.*

class RecipeAdapter(private var items: List<Food>, private val listener: Listener)
    : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    interface Listener {
        fun onClickItem(item: Food)
        fun onAddFavorite(item: Food)
        fun onRemoveFavorite(item: Food)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], listener)

    override fun getItemCount() = items.size

    fun removeItem(item: Food) {
        items -= item
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Food, listener: Listener) = with(itemView) {
            Glide.with(context).load(item.imageUrl.replace("http", "https")).into(imageView)
            title.text = item.title

            if (item.isFavorited) {
                favButton.setImageResource(R.drawable.ic_favorite_24dp)
            } else {
                favButton.setImageResource(R.drawable.ic_favorite_border_24dp)
            }

            setOnClickListener {
                listener.onClickItem(item)
            }

            favButton.setOnClickListener {
                if (item.isFavorited) {
                    listener.onRemoveFavorite(item)
                } else {
                    listener.onAddFavorite(item)
                }
            }
        }
    }
}