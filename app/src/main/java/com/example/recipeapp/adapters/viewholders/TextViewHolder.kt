package com.example.recipeapp.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.adapters.OnTextClickListener
import com.example.recipeapp.databinding.TextHeaderItemBinding

class TextViewHolder(val binding: TextHeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: OnTextClickListener) {
             binding.seeHeaderText.setOnClickListener {
                 clickListener.onClickListener()
             }

        }

    companion object {
        fun from(parent: ViewGroup): TextViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = TextHeaderItemBinding.inflate(layoutInflater, parent, false)

            return TextViewHolder(view)
        }
    }


}