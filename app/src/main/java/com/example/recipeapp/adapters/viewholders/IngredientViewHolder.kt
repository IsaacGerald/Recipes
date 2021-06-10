package com.example.recipeapp.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.adapters.IngredientClickListener
import com.example.recipeapp.adapters.IngredientOnclickListener
import com.example.recipeapp.databinding.*
import com.example.recipeapp.db.CartRecipes

class IngredientViewHolder(val binding: IngredientsListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val TAG = IngredientViewHolder::class.java.simpleName
    fun bind(item: CartRecipes?, clickListener: IngredientClickListener) {
        if (item == null) return

        binding.ingr = item

        val imageBtn = binding.addImageView


        handleClick(imageBtn, clickListener, item)

        binding.invalidateAll()

    }

    private fun handleClick(
        imageBtn: ImageButton,
        clickListener: IngredientClickListener,
        item: CartRecipes
    ) {
        var isClicked: Boolean

        isClicked = if (item.isAddedToCart){
            imageBtn.setImageResource(R.drawable.ic_check_circle_24)
            false
        }else{
            imageBtn.setImageResource(R.drawable.ic_add_circle_24)
            true
        }

        imageBtn.setOnClickListener {
            isClicked = if (isClicked) {
                clickListener.onClick(isClicked, item)
                //Toast.makeText(it.context, " item clicked!", Toast.LENGTH_SHORT).show()
                imageBtn.setImageResource(R.drawable.ic_check_circle_24)
                false
            } else {
                clickListener.onClick(isClicked, item)
                //Toast.makeText(it.context, " item is unClicked!", Toast.LENGTH_SHORT).show()
                //imageBtn.setImageResource(R.drawable.ic_add_circle_24)
                imageBtn.setImageResource(R.drawable.ic_add_circle_24)
                true
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): IngredientViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val bind = IngredientsListItemBinding.inflate(layoutInflater, parent, false)


            return IngredientViewHolder(bind)
        }
    }
}