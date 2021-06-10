package com.example.recipeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [DomainRecipe::class, BookmarkedRecipes::class, FavoriteRecipes::class, SearchRecipes::class, CartRecipes::class, IngredientRecipe::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(CuisinesTypeConverters::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun favDao(): FavoriteDao
    abstract fun bmDao(): BookMarkDao
    abstract fun searchDao(): SearchDao
    abstract fun CartDao(): CartDao
    abstract fun ingredientRecipeDao(): IngredientRecipeDao


    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null


        fun getInstance(context: Context): FoodDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FoodDatabase::class.java, "FoodDatabase"
            )
                .fallbackToDestructiveMigration()
                .build()
    }


}