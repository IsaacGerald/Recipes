package com.example.recipeapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.db.*


import com.example.recipeapp.network.FoodApiService
import com.example.recipeapp.network.FoodRecipeContainer
import com.example.recipeapp.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.concurrent.Flow

class FoodRepository(private val database: FoodDatabase, private val service: FoodApiService) {
    private val TAG = FoodRepository::class.java.simpleName
    var currentQuery: String? = null
    private val _response = MutableLiveData<String>()

    // val searchResponse = MutableLiveData<List<DomainRecipe>>()
    val searchResponse = MutableLiveData<Response<FoodRecipeContainer>>()
    val response: LiveData<String>
        get() = _response;

    suspend fun refreshFoodRecipes(queryString: String?) {

        if (queryString != null) {

            try {
                withContext(Dispatchers.IO) {
                    val response =
                        service.getFoodTypes(query = queryString)
                    searchResponse.postValue(response)
                    if (response.isSuccessful) {
                        database.foodDao().clearAll()
                        val recipe = response.body()
                        Log.d(
                            TAG,
                            "refreshFoodRecipes: response successful ${response.message()}"
                        )
                        val fetchedDomainRecipes = recipe?.asDomainModel()
                        val domainRecipe = getType(fetchedDomainRecipes, queryString)
                        database.foodDao().insertAll(domainRecipe)

                    } else {
                        _response.value = response.message()
                    }

                }
            } catch (e: Exception) {

                _response.value = e.message
            }

        }

    }

    private fun getType(
        fetchedDomainRecipes: List<DomainRecipe>?,
        queryString: String?
    ): List<DomainRecipe> {
        val domainRecipe: MutableList<DomainRecipe> = mutableListOf()

        if (fetchedDomainRecipes != null) {
            for (el in fetchedDomainRecipes) {
                el.type = queryString
                domainRecipe.add(el)
            }
        }

        return mergeFavorites(domainRecipe)
    }

    private fun mergeFavorites(domainRecipe: List<DomainRecipe>): List<DomainRecipe> {
        val favRecipes: List<FavoriteRecipes>? = database.favDao().getFavorites()

        if (favRecipes != null) {
            for (recipe in domainRecipe) {
                for (favorite in favRecipes) {
                    if (recipe.label == favorite.label)
                        recipe.liked = true
                }
            }
        }

        return domainRecipe
    }

    suspend fun clearAllRecipes() =
        database.foodDao().clearAll()


    /*-----------------Shopping Fragment--------------------------*/

    fun getAlIngredientsRecipes(): LiveData<List<IngredientRecipe>> {
        return database.ingredientRecipeDao().getAllIngredientRecipes()
    }

    fun getAllCartRecipes(): LiveData<List<CartRecipes>> {
        return database.CartDao().getAllCartRecipes()
    }


    /*-----------------Favorite Fragment--------------------------*/

    suspend fun addFavorites(recipe: FavoriteRecipes) {
        Log.d(TAG, "addFavorites: Inserting data to favorite_table")
        withContext(Dispatchers.IO) {
            database.favDao().insertToFavorites(recipe)
        }
    }

    suspend fun deleteFavorite(label: String) {
        withContext(Dispatchers.IO) {
            database.favDao().deleteFavorite(label)
        }
    }

    fun getFavoriteRecipe(label: String): LiveData<FavoriteRecipes>? {
        return database.favDao().getFavoriteRecipe(label)

    }

    suspend fun clearFavorites() {
        withContext(Dispatchers.IO) {
            database.favDao().clearAll()
        }
    }

    fun getAllFavoriteRecipes(): LiveData<List<FavoriteRecipes>>? {
        return database.favDao().getAllFavoriteRecipes()
    }

    /*-----------------Bookmark Fragment--------------------------*/

    fun getBookmarkedRecipes(): LiveData<List<BookmarkedRecipes>>? {
        return database.bmDao().getBookmarkedRecipes()
    }

    suspend fun addBookMark(recipe: BookmarkedRecipes?) {
        withContext(Dispatchers.IO) {
            database.bmDao().insertToBookmarks(recipe)
        }
    }

    suspend fun deleteBookmark(url: String) {
        withContext(Dispatchers.IO) {
            database.bmDao().deleteBookmark(url)
        }
    }

    suspend fun clearBookmark() {
        withContext(Dispatchers.IO) {
            database.bmDao().clearAll()
        }
    }

    /*-----------------Search Fragment--------------------------*/

    fun getRecentSearchedRecipes(): LiveData<List<SearchRecipes>> {
        return database.searchDao().getAllSearchedRecipes()
    }

    fun getSearchRecipe(type: String) =
        database.searchDao().getSearchRecipe(type)


    fun getRecentFiveRecipes(): LiveData<List<SearchRecipes>> {
        return database.searchDao().getFirstFiveRecipes()
    }

    suspend fun deleteSearchRecipe(id: Long) {
        withContext(Dispatchers.IO) {
            database.searchDao().deleteRecipe(id)
        }
    }

    suspend fun clearAllRecentRecipes() =
        database.searchDao().clearAllRecentRecipes()


    suspend fun insertSearchRecipe(recipe: SearchRecipes) {
        withContext(Dispatchers.IO) {
            database.searchDao().insertRecipe(recipe)
        }
    }

    suspend fun getSearchRecentRecipe(query: String) =
        database.searchDao().getRecentSearchRecipe("%$query%")

     fun getSearchRecipes(type: String) =
        database.foodDao().getRecipe(type)


    /*-------------Recipe Fragment ---------------------------*/

    fun getRecipeIngredient(label: String): LiveData<IngredientRecipe> {
        return database.ingredientRecipeDao().getRecipe(label)
    }

    //Add Ingredients to Cart
    suspend fun addIngredientToCart(ingr: CartRecipes) {
        withContext(Dispatchers.IO) {
            database.CartDao().insertIngredient(ingr)
        }
    }

    //Delete ingredients from Cart
    suspend fun deleteIngredientFromCart(ingr: String) {
        withContext(Dispatchers.IO) {
            database.CartDao().deleteIngredient(ingr)
        }
    }

    //Get IngredientsFrom Cart
    fun getCartIngredients(label: String): LiveData<List<CartRecipes>> {
        return database.CartDao().getIngredients(label)

    }

    //Add Ingredient to Ingredient recipe table
    suspend fun addToIngredientRecipe(recipe: IngredientRecipe) {
        withContext(Dispatchers.IO) {
            database.ingredientRecipeDao().insertRecipe(recipe)
        }
    }

    //delete ingredient from ingredient recipe table
    suspend fun deleteIngredientRecipe(label: String) {
        withContext(Dispatchers.IO) {
            database.ingredientRecipeDao().deleteIngredient(label)
        }
    }


}