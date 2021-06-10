package com.example.recipeapp.mealdetails

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.db.DomainRecipe
import com.example.recipeapp.models.Recipe
import com.example.recipeapp.network.asBookmarkRecipe
import com.example.recipeapp.network.asFavoriteRecipe
import com.example.recipeapp.repository.FoodRepository
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipe: DomainRecipe, private val repository: FoodRepository) : ViewModel() {

    private val _selectedRecipe = MutableLiveData<DomainRecipe>()

    val selectedRecipe: LiveData<DomainRecipe>
        get() = _selectedRecipe


    init {
        displayRecipe()
    }

    private fun displayRecipe() {
        _selectedRecipe.value = recipe
    }

    fun addToBookmarks() {
        viewModelScope.launch {
            repository.addBookMark(recipe.asBookmarkRecipe())
        }
    }

    fun addToFavorites(){
        viewModelScope.launch {
            repository.addFavorites(recipe.asFavoriteRecipe())
        }
    }

}