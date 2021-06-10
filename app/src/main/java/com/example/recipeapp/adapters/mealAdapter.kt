package com.example.recipeapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.adapters.MealAdapter.MealViewHolder.Companion.from
import com.example.recipeapp.databinding.MealItemListBinding
import com.example.recipeapp.db.DomainRecipe
import com.example.recipeapp.models.Recipe

class MealAdapter(private val onclickListener: OnclickListener? = null) :
    ListAdapter<DomainRecipe, MealAdapter.MealViewHolder>(MealDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onclickListener)

    }


    class MealViewHolder(private val binding: MealItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DomainRecipe?, onclickListener: OnclickListener?) {
            if (item == null) return
            binding.recipe = item
            binding.invalidateAll()
            binding.clicklistener = onclickListener


        }

        companion object {
            fun from(parent: ViewGroup): MealViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bind = MealItemListBinding.inflate(layoutInflater, parent, false)
                return MealViewHolder(bind)
            }
        }

    }


    companion object MealDiffCallBack : DiffUtil.ItemCallback<DomainRecipe>() {
        override fun areItemsTheSame(oldItem: DomainRecipe, newItem: DomainRecipe): Boolean {
            return oldItem == oldItem
        }

        override fun areContentsTheSame(oldItem: DomainRecipe, newItem: DomainRecipe): Boolean {
            return oldItem.uri.equals(newItem.uri) &&
                    oldItem.url.equals(newItem.url)

        }


    }


}

class OnclickListener(val clickListener: (recipe: DomainRecipe) -> Unit) {
    fun onClick(recipe: DomainRecipe) = clickListener(recipe)
}