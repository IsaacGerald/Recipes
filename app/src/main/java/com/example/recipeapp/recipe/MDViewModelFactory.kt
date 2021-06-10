package com.example.recipeapp.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.db.DomainRecipe
import com.example.recipeapp.repository.FoodRepository
import java.lang.IllegalArgumentException

class MDViewModelFactory(private val args: DomainRecipe, private val repository: FoodRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MealDetailViewModel(args, repository) as T
        }else{
            throw IllegalArgumentException("Unknown viewModel class")
        }
    }
}