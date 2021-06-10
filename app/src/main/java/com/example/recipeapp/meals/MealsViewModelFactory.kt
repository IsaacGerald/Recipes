package com.example.recipeapp.meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.repository.FoodRepository

class MealsViewModelFactory(private val modelName: String,
                            val id: Int,
                            private val repository: FoodRepository
                            ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MealsViewModel(modelName, id, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}