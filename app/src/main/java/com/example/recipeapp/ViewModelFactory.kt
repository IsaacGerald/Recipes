package com.example.recipeapp

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.recipeapp.favorite.FavoriteViewModel
import com.example.recipeapp.home.HomeViewModel
import com.example.recipeapp.repository.FoodRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val foodRepository: FoodRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
): AbstractSavedStateViewModelFactory(owner, defaultArgs){
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass){
        when{
            isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(foodRepository, handle)
            isAssignableFrom(FavoriteViewModel::class.java) ->
                FavoriteViewModel(foodRepository)
            else ->
                throw IllegalArgumentException("Unknown viewModel class")
        }
    } as T





}
