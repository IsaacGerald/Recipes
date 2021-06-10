package com.example.recipeapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.recipeapp.adapters.viewholders.RecentSearchViewHolder
import com.example.recipeapp.adapters.viewholders.SearchViewHolder
import com.example.recipeapp.adapters.viewholders.TextViewHolder
import com.example.recipeapp.db.SearchRecipes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.ClassCastException


class SearchAdapter(private val clickListener: OnSearchClickListener) :
    ListAdapter<SearchRecipes, SearchViewHolder>(SearchDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        return SearchViewHolder.from(parent)

    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)


    }

    companion object SearchDiffCallback : DiffUtil.ItemCallback<SearchRecipes>() {
        override fun areItemsTheSame(oldItem: SearchRecipes, newItem: SearchRecipes): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SearchRecipes, newItem: SearchRecipes): Boolean {
            return oldItem.uri.equals(newItem.uri) &&
                    oldItem.url.equals(newItem.url)

        }

    }

}



