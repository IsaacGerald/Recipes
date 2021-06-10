package com.example.recipeapp.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapters.OnSearchClickListener
import com.example.recipeapp.databinding.SearchItemListBinding
import com.example.recipeapp.db.SearchRecipes

class SearchViewHolder(val binding: SearchItemListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SearchRecipes?, clickListener: OnSearchClickListener) {
        if (item != null) {
            binding.recipe = item
            binding.invalidateAll()
            binding.clickListener = clickListener

        }
    }

    companion object {
        fun from(parent: ViewGroup): SearchViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val bind = SearchItemListBinding.inflate(layoutInflater, parent, false)

            return SearchViewHolder(bind)
        }
    }


}