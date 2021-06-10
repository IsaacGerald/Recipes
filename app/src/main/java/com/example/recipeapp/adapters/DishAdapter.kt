package com.example.recipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.adapters.DishAdapter.DishViewHolder.Companion.from
import com.example.recipeapp.databinding.DishItemListBinding
import com.example.recipeapp.db.DomainRecipe

class DishAdapter(
    private val clickListener: DishItemClickListener,
    private val favClickListener: FavItemClickListener
) : ListAdapter<DomainRecipe, DishAdapter.DishViewHolder>(DishItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, favClickListener)
    }

    class DishViewHolder(val binding: DishItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DishViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bind = DishItemListBinding.inflate(layoutInflater, parent, false)
                return DishViewHolder(bind)
            }
        }


        fun bind(
            type: DomainRecipe,
            clickListener: DishItemClickListener,
            favClickListener: FavItemClickListener
        ) {

            binding.meal = type
            if (type.liked){
                binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_filled_24)
            }else{
                binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_24)
            }
            binding.clickListener = clickListener
            binding.favClickListener = favClickListener
            binding.mealsImageView.clipToOutline = true
            binding.executePendingBindings()


        }

    }

    object DishItemCallback : DiffUtil.ItemCallback<DomainRecipe>() {
        override fun areItemsTheSame(oldItem: DomainRecipe, newItem: DomainRecipe): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: DomainRecipe, newItem: DomainRecipe): Boolean {
            return oldItem == newItem
        }

    }

}

class DishItemClickListener(val clickListener: (meal: DomainRecipe) -> Unit) {
    fun onClick(meal: DomainRecipe) = clickListener(meal)
}

class FavItemClickListener(val clickListener: (meal: DomainRecipe) -> Unit) {
    fun onClick(meal: DomainRecipe) = clickListener(meal)
}

