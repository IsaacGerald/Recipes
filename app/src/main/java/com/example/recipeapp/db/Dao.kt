package com.example.recipeapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.models.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipe: List<DomainRecipe>?)

    @Query("SELECT * FROM recipe_table WHERE type= :type")
    fun getRecipe(type: String): LiveData<List<DomainRecipe>>

    @Query("DELETE FROM recipe_table")
    suspend fun clearAll()


}

@Dao
interface BookMarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToBookmarks(recipe: BookmarkedRecipes?)

    @Query("DELETE FROM bookmarked_recipes WHERE url= :url")
    suspend fun deleteBookmark(url: String)

    @Query("DELETE FROM bookmarked_recipes")
    suspend fun clearAll()

    @Query("SELECT * FROM bookmarked_recipes")
    fun getBookmarkedRecipes(): LiveData<List<BookmarkedRecipes>>?
}

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavorites(recipe: FavoriteRecipes)

    @Query("DELETE FROM favorite_recipes WHERE label= :label")
    suspend fun deleteFavorite(label: String)

    @Query("DELETE FROM favorite_recipes")
    suspend fun clearAll()

    @Query("SELECT * FROM favorite_recipes")
    fun getAllFavoriteRecipes(): LiveData<List<FavoriteRecipes>>?

    @Query("SELECT * FROM favorite_recipes")
    fun getFavorites(): List<FavoriteRecipes>?

    @Query("SELECT * FROM favorite_recipes WHERE label= :label")
    fun getFavoriteRecipe(label: String): LiveData<FavoriteRecipes>?
}

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipes: SearchRecipes?)


    @Query("SELECT * FROM search_table ORDER BY id DESC")
    fun getAllSearchedRecipes(): LiveData<List<SearchRecipes>>

    @Query("SELECT * FROM search_table  ORDER BY id DESC LIMIT 5")
    fun getFirstFiveRecipes(): LiveData<List<SearchRecipes>>

    @Query("SELECT * FROM search_table WHERE type= :type ")
    fun getSearchRecipe(type: String): LiveData<List<SearchRecipes>>

    @Query("SELECT * FROM search_table WHERE label LIKE :query OR source LIKE :query ")
    suspend fun getRecentSearchRecipe(query: String): List<SearchRecipes>

    @Query("DELETE FROM search_table WHERE id= :id")
    suspend fun deleteRecipe(id: Long)

    @Query("DELETE FROM search_table")
    suspend fun clearAllRecentRecipes()
}

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(recipe: CartRecipes)

    @Query("SELECT * FROM cart_table WHERE label= :label")
    fun getIngredients(label: String): LiveData<List<CartRecipes>>

    @Query("DELETE  FROM cart_table WHERE ingredient= :ingredient")
    fun deleteIngredient(ingredient: String)

    @Query("UPDATE cart_table SET bought= :bought WHERE label= :label")
    fun updatePurchase(label: String, bought: Boolean)

    @Query("SELECT * FROM cart_table")
    fun getAllCartRecipes(): LiveData<List<CartRecipes>>


}


@Dao
interface IngredientRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: IngredientRecipe)

    @Query("SELECT * FROM ingredient_recipes WHERE label= :label")
    fun getRecipe(label: String): LiveData<IngredientRecipe>

    @Query("DELETE  FROM ingredient_recipes WHERE label= :label")
    fun deleteIngredient(label: String)

    @Query("SELECT * FROM ingredient_recipes")
    fun getAllIngredientRecipes(): LiveData<List<IngredientRecipe>>


}

