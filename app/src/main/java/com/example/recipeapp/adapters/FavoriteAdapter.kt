package com.example.recipeapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.recipeapp.adapters.viewholders.FavoriteViewHolder
import com.example.recipeapp.db.FavoriteRecipes

class FavoriteAdapter(private val onclickListener: OnProfileClickListener? = null) :
    ListAdapter<FavoriteRecipes, FavoriteViewHolder>(ProfileDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onclickListener)

    }


    companion object ProfileDiffCallBack : DiffUtil.ItemCallback<FavoriteRecipes>() {
        override fun areItemsTheSame(oldItem: FavoriteRecipes, newItem: FavoriteRecipes): Boolean {
            return oldItem == oldItem
        }

        override fun areContentsTheSame(oldItem: FavoriteRecipes, newItem: FavoriteRecipes): Boolean {
            return oldItem.uri.equals(newItem.uri) &&
                    oldItem.url.equals(newItem.url)

        }

    }

}

class OnProfileClickListener(val clickListener: (recipe: FavoriteRecipes) -> Unit) {
    fun onClick(recipe: FavoriteRecipes) = clickListener(recipe)
}