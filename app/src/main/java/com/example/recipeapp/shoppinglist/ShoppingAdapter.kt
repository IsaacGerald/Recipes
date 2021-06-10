package com.example.recipeapp.shoppinglist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ShoppingListItemBinding
import com.example.recipeapp.models.Ingredients


class ShoppingAdapter() :
    ListAdapter<Ingredients, ShoppingAdapter.MyViewHolder>(ShoppingDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


     class MyViewHolder(private val binding: ShoppingListItemBinding) :
        RecyclerView.ViewHolder(binding.root), ShoppingIngrClickListener {
         private  val TAG =  ShoppingAdapter::class.java.simpleName
        fun bind(item: Ingredients?) {

            if (item != null) {
                binding.recipe = item
                val adapter = ShoppingIngredientAdapter(this)
                binding.ingredientListRv.adapter = adapter
                adapter.submitList(item.ingredients)
            }

        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bind = ShoppingListItemBinding.inflate(inflater, parent, false)

                return MyViewHolder(bind)
            }
        }



        override fun onIngredientClick(bool: Boolean, item: String) {
            if (bool) {
                Log.d(TAG, "onIngredientClick:" + item +"is clicked")
                binding.recipeTxt
            } else {
                Log.d(TAG, "onIngredientClick:" + item +"is unClicked")
            }
        }


    }



    companion object ShoppingDiffCallback : DiffUtil.ItemCallback<Ingredients>() {
        override fun areItemsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
            return oldItem.name == newItem.name
        }

    }

    interface ShoppingIngrClickListener{
        fun onIngredientClick(bool: Boolean, item: String)
    }

}