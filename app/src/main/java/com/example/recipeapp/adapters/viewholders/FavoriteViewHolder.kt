package com.example.recipeapp.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapters.OnProfileClickListener
import com.example.recipeapp.databinding.FavoriteListItemBinding
import com.example.recipeapp.db.FavoriteRecipes

class FavoriteViewHolder(val binding: FavoriteListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: FavoriteRecipes?, onclickListener: OnProfileClickListener?) {
        if (item == null) return
        binding.recipe = item
        binding.invalidateAll()
        binding.profileImageView.clipToOutline = true
        binding.clickListener = onclickListener

    }

    companion object {
        fun from(parent: ViewGroup): FavoriteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val bind = FavoriteListItemBinding.inflate(layoutInflater, parent, false)
            return FavoriteViewHolder(bind)
        }
    }
}