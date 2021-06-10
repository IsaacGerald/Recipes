package com.example.recipeapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.recipeapp.adapters.viewholders.IngredientViewHolder
import com.example.recipeapp.db.CartRecipes

class IngredientsAdapter(private val clickListener: IngredientClickListener) :
    ListAdapter<CartRecipes, IngredientViewHolder>(MealDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)

    }

    companion object MealDiffCallBack : DiffUtil.ItemCallback<CartRecipes>() {
        override fun areItemsTheSame(oldItem: CartRecipes, newItem: CartRecipes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartRecipes, newItem: CartRecipes): Boolean {
            return oldItem == newItem

        }


    }


}

interface IngredientClickListener{
    fun onClick(isClicked: Boolean, item: CartRecipes)
}

class IngredientOnclickListener(val clickListener: (ingredient: CartRecipes) -> Unit) {
    fun onClick(ingredient: CartRecipes) = clickListener(ingredient)
}