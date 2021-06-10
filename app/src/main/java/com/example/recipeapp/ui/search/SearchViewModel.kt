package com.example.recipeapp.ui.search

import androidx.lifecycle.*
import com.example.recipeapp.db.SearchRecipes
import com.example.recipeapp.network.FoodRecipeContainer
import com.example.recipeapp.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(val repository: FoodRepository) : ViewModel() {


    private val _selectedRecipe = MutableLiveData<SearchRecipes?>();

    val response: LiveData<String>
    get() = repository.response

    val selectedRecipe: LiveData<SearchRecipes?>
        get() = _selectedRecipe

    private val _isSearchViewVisible = MutableLiveData<Boolean>()

    val isSearchViewVisible: LiveData<Boolean>
        get() = _isSearchViewVisible

    private val _newRecipes = MutableLiveData<List<SearchRecipes>?>()

    val newRecipes: LiveData<List<SearchRecipes>?>
        get() = _newRecipes

    private val _searchRecipes = MutableLiveData<List<SearchRecipes>>()

    val searchRecipes: LiveData<List<SearchRecipes>>
    get() = _searchRecipes

    val recentlySearchedRecipes: LiveData<List<SearchRecipes>>
    get() = repository.getRecentSearchedRecipes()

    fun deleteRecipe(id: Long) =
        viewModelScope.launch {
            repository.deleteSearchRecipe(id)
        }

    fun getSearchRecentRecipe(query: String) = liveData(Dispatchers.IO) {
        emit(repository.getSearchRecentRecipe(query))
    }

    fun searchedRecipe(query: String) =
         repository.getSearchRecipes(query)

    val searchResponse: LiveData<Response<FoodRecipeContainer>>
    get() = repository.searchResponse

    val networkResponse: LiveData<String>
        get() = repository.response;

    fun clearRecipes() =
        viewModelScope.launch {
            repository.clearAllRecipes()
        }


     fun clearAllRecentRecipes() =
        viewModelScope.launch {
            repository.clearAllRecentRecipes()
        }


    fun searchRecipe(queryString: String) {
        viewModelScope.launch {
            repository.refreshFoodRecipes(queryString)
        }


    }

    fun displaySearchRecipe(recipes: SearchRecipes) {
        _selectedRecipe.value = recipes
    }

    fun displaySearchRecipeCompleted() {
        _selectedRecipe.value = null
    }

    fun insertSearchRecipe(recipe: SearchRecipes) {
        viewModelScope.launch {
            repository.insertSearchRecipe(recipe)
        }
    }

    fun hideSearchView() {
        _isSearchViewVisible.value = false
    }

    fun displaySearchView() {
        _isSearchViewVisible.value = true
    }

    fun searchList(searchRecipes: List<SearchRecipes>) {
        _searchRecipes.value = searchRecipes
    }


}