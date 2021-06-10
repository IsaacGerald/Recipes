package com.example.recipeapp.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapters.OnSavedClickListener
import com.example.recipeapp.databinding.BookmarkListItemBinding
import com.example.recipeapp.db.BookmarkedRecipes

class BookmarkViewHolder(val binding: BookmarkListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BookmarkedRecipes?, onclickListener: OnSavedClickListener?) {
        if (item == null) return
        binding.recipe = item
        binding.invalidateAll()
        binding.clickListener = onclickListener

    }

    companion object {
        fun from(parent: ViewGroup): BookmarkViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val bind = BookmarkListItemBinding.inflate(layoutInflater, parent, false)
            return BookmarkViewHolder(bind)
        }
    }
}