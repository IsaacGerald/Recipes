package com.example.recipeapp.mealdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.db.DomainRecipe

import com.example.recipeapp.repository.FoodRepository

class RecipeViewModelFactory(val recipe: DomainRecipe, val repository: FoodRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)){
            return RecipeViewModel(recipe, repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}