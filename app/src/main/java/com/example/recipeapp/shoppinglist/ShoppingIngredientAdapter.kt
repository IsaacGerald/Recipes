package com.example.recipeapp.shoppinglist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ShoppingIngredientListItemBinding


class ShoppingIngredientAdapter(private val clickListener: ShoppingAdapter.ShoppingIngrClickListener) :
    ListAdapter<String, ShoppingIngredientAdapter.MyViewHolder>(IngDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class MyViewHolder(private val binding: ShoppingIngredientListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String?, clickListener: ShoppingAdapter.ShoppingIngrClickListener) {
            if (item != null) {
                var text = binding.ingredientTxt
                binding.ingr = item
                var isClicked = true

                binding.ingredientTxt.setOnClickListener {
                    isClicked = if (isClicked){
                         text = binding.ingredientTxt
                        text.paintFlags = text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        clickListener.onIngredientClick(true, item)
                        false
                    }else{
                        text.paintFlags = Paint.ANTI_ALIAS_FLAG
                        clickListener.onIngredientClick(false, item)
                        true
                    }
                }


                binding.invalidateAll()
            }


        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bind = ShoppingIngredientListItemBinding.inflate(inflater, parent, false)

                return MyViewHolder(bind)
            }
        }


    }


    companion object IngDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

}

