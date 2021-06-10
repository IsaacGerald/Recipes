package com.example.recipeapp.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.db.FavoriteRecipes
import com.example.recipeapp.repository.FoodRepository

class FavoriteViewModel(private val repository: FoodRepository) : ViewModel() {

    val getFavRecipes: LiveData<List<FavoriteRecipes>>?
        get() = repository.getAllFavoriteRecipes()

    private val _navigateToMeal = MutableLiveData<FavoriteRecipes?>()
    val navigateToMeal: MutableLiveData<FavoriteRecipes?>
        get() = _navigateToMeal


    fun navigateToSelectedMeal(it: FavoriteRecipes) {
        _navigateToMeal.value = it
    }

    fun navigationCompleted() {
        _navigateToMeal.value = null
    }


}