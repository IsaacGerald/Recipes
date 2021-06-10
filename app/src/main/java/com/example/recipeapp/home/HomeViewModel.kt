package com.example.recipeapp.home

import androidx.lifecycle.*
import com.example.recipeapp.db.DomainRecipe
import com.example.recipeapp.models.*

import com.example.recipeapp.network.FoodRecipeContainer
import com.example.recipeapp.network.asFavoriteRecipe
import com.example.recipeapp.repository.FoodRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    private val repository: FoodRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName
    private val _response = MutableLiveData<String>()
    private val _foodResponse = MutableLiveData<FoodRecipe>()
    private val _navigateToSelectedCategory = MutableLiveData<Categories?>()
    private val _navigateToSelectedMeal = MutableLiveData<DomainRecipe?>()
    private val _navigateToSelectedCuisine = MutableLiveData<Cuisine?>()
    private val _queryString = MutableLiveData<String?>()
    val queryString: LiveData<String?>
        get() = _queryString

    val navigateToSelectedMeal: LiveData<DomainRecipe?>
        get() = _navigateToSelectedMeal


    fun getSearchRecipeFromDatabase(text: String): LiveData<List<DomainRecipe>> =
        repository.getSearchRecipes(text)


    val fetchResponse: LiveData<Response<FoodRecipeContainer>>
        get() = repository.searchResponse

    fun fetchRecipe(type: String) {
        viewModelScope.launch {
            repository.refreshFoodRecipes(type)
        }
    }

    fun addToFavorites(recipe: DomainRecipe) =
        viewModelScope.launch {
            repository.addFavorites(recipe.asFavoriteRecipe())
        }



    val response: LiveData<String>
        get() = _response


    fun displayMeal(meal: DomainRecipe) {
        _navigateToSelectedMeal.value = meal
    }

    fun displayMealCompleted() {
        _navigateToSelectedMeal.value = null
    }

    fun setSearchQuery(query: String) {
        _queryString.value = query
    }


}