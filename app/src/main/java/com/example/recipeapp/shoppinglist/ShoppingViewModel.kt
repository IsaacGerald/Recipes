package com.example.recipeapp.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.db.CartRecipes
import com.example.recipeapp.db.IngredientRecipe
import com.example.recipeapp.models.Ingredients
import com.example.recipeapp.repository.FoodRepository

class ShoppingViewModel(private val repository: FoodRepository) : ViewModel() {

    val ingredientRecipes: LiveData<List<IngredientRecipe>>
        get() = repository.getAlIngredientsRecipes()

    val cartRecipes: LiveData<List<CartRecipes>>
        get() = repository.getAllCartRecipes()

    //   val ingredients = MediatorLiveData<List<Ingredients>>()

//    init {
//
//        ingredients.addSource( ingredientRecipes -> in)
//        ingredients.addSource(ingredientRecipes){ result: List<IngredientRecipe> ->
//            result.let {
//                val shoppingList = mutableListOf<Ingredients>()
//                val ingredientList = mutableListOf<String>()
//
//                for (recipes in result){
//                    for (ingredients in cartRecipes.value!!){
//                        if (recipes.label == ingredients.label){
//                            ingredientList.add(ingredients.ingredient)
//                        }
//                        shoppingList.add(Ingredients(recipes.label, ingredientList))
//                        ingredientList.clear()
//                    }
//                }
//                ingredients.value = shoppingList
//            }
//        }
//    }

}