package com.example.recipeapp.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapters.OnDeleteClickListener
import com.example.recipeapp.adapters.OnSearchClickListener
import com.example.recipeapp.adapters.OnImageClickListener
import com.example.recipeapp.databinding.RecentSearchItemListBinding
import com.example.recipeapp.db.SearchRecipes

class RecentSearchViewHolder(val binding: RecentSearchItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: SearchRecipes?,
        clickListener: OnSearchClickListener,
        imageClickListener: OnImageClickListener,
        onDeleteClickListener: OnDeleteClickListener
    ) {
        if (item != null) {
            binding.recipe = item
            binding.invalidateAll()
            binding.clickListener = clickListener
            binding.onDeleteClickListener = onDeleteClickListener

            binding.ImageViewRecentList.setOnLongClickListener { view ->
                imageClickListener.onImageClickListener(item, view)
                false
            }


        }
    }

    companion object {
        fun from(parent: ViewGroup): RecentSearchViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val bind = RecentSearchItemListBinding.inflate(layoutInflater, parent, false)

            return RecentSearchViewHolder(bind)
        }
    }


}