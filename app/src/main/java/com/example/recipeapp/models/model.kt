package com.example.recipeapp.models

import com.example.recipeapp.R
import com.example.recipeapp.network.FoodApiTypes

data class Categories(val name:String, val id: Int)
data class Cuisine(val name:String, val id: Int)
data class Meal(val name:String, val id: Int)
data class Ingredients(val name: String, val ingredients: List<String>)
data class Slider(val id: Int, val image: Int)





