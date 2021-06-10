package com.example.recipeapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.recipeapp.adapters.viewholders.BookmarkViewHolder
import com.example.recipeapp.db.BookmarkedRecipes

class SavedAdapter(private val onclickListener: OnSavedClickListener? = null) :
    ListAdapter<BookmarkedRecipes, BookmarkViewHolder>(BookmarkDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onclickListener)

    }


    companion object BookmarkDiffCallBack : DiffUtil.ItemCallback<BookmarkedRecipes>() {
        override fun areItemsTheSame(oldItem: BookmarkedRecipes, newItem: BookmarkedRecipes): Boolean {
            return oldItem == oldItem
        }

        override fun areContentsTheSame(oldItem: BookmarkedRecipes, newItem: BookmarkedRecipes): Boolean {
            return oldItem.uri.equals(newItem.uri) &&
                    oldItem.url.equals(newItem.url)

        }

    }

}

class OnSavedClickListener(val clickListener: (recipe: BookmarkedRecipes) -> Unit) {
    fun onClick(recipe: BookmarkedRecipes) = clickListener(recipe)
}