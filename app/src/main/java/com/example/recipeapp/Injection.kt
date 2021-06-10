package com.example.recipeapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.db.FoodDatabase
import com.example.recipeapp.meals.MealsViewModelFactory
import com.example.recipeapp.network.FoodApi
import com.example.recipeapp.network.FoodApiService
import com.example.recipeapp.repository.FoodRepository

object Injection {


    private fun provideDatabase(context: Context): FoodDatabase{
        return FoodDatabase.getInstance(context)
    }

    private fun provideService(): FoodApiService{
        return FoodApi.retrofitService
    }

    fun provideFoodRepository(context: Context): FoodRepository{
        return FoodRepository(provideDatabase(context), provideService())
    }

    fun Fragment.getViewModelFactory(context: Context): ViewModelFactory {
        return ViewModelFactory(provideFoodRepository(context), this)
    }




}