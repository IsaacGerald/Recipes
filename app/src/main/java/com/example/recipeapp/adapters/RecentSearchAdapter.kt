package com.example.recipeapp.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapters.viewholders.RecentSearchViewHolder
import com.example.recipeapp.adapters.viewholders.TextViewHolder
import com.example.recipeapp.db.SearchRecipes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class RecentSearchAdapter(
    private val clickListener: OnSearchClickListener,
    private val textClickListener: OnTextClickListener,
    private val imageClickListener: OnImageClickListener,
    private val onDeleteClickListener: OnDeleteClickListener
) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(SearchDiffCallback) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> RecentSearchViewHolder.from(parent)
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }


    }

    fun addHeaderAndSubmitList(list: List<SearchRecipes>) {
        adapterScope.launch {
            val items = if (!list.isNullOrEmpty()){
                listOf(DataItem.Header) + list.map {
                    DataItem.SearchItem(it)
            }}else{
                null
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SearchItem -> ITEM_VIEW_TYPE_ITEM
            else -> super.getItemViewType(position)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is RecentSearchViewHolder -> {
                val item = getItem(position) as DataItem.SearchItem
                holder.bind(item.recipe, clickListener, imageClickListener, onDeleteClickListener)
            }
            is TextViewHolder -> {
                holder.bind(textClickListener)
            }
        }

    }


    companion object SearchDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem

        }

    }

}

class OnSearchClickListener(val clickListener: (recipe: SearchRecipes) -> Unit) {
    fun onClick(recipe: SearchRecipes) = clickListener(recipe)
}

class OnDeleteClickListener(val clickListener: (recipe: SearchRecipes) -> Unit) {
    fun onClick(recipe: SearchRecipes) = clickListener(recipe)
}

interface OnImageClickListener{
    fun onImageClickListener(item: SearchRecipes, view: View)
}


interface OnTextClickListener {
    fun onClickListener()
}


sealed class DataItem {
    abstract val id: Long

    data class SearchItem(val recipe: SearchRecipes) : DataItem() {
        override val id: Long
            get() = recipe.id
    }

    object Header : DataItem() {
        override val id: Long
            get() = Long.MIN_VALUE

    }
}