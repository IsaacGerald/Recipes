package com.example.recipeapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.repository.FoodRepository

class SearchViewModelFactory(val repository: FoodRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }else{
            throw IllegalArgumentException("Unknown viewModel class")
        }
    }
}