package com.example.recipeapp.shoppinglist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.Injection
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentShoppingBinding
import com.example.recipeapp.db.CartRecipes
import com.example.recipeapp.db.IngredientRecipe
import com.example.recipeapp.models.DataManager
import com.example.recipeapp.models.Ingredients


class ShoppingFragment : Fragment() {
    private lateinit var binding: FragmentShoppingBinding
    private lateinit var viewModel: ShoppingViewModel
    private val ingredientRecipes = mutableListOf<IngredientRecipe>()
    private val cartRecipes = mutableListOf<CartRecipes>()
    private val TAG = ShoppingFragment::class.java.simpleName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping, container, false)
        val repository = Injection.provideFoodRepository(this.requireContext())
        val viewModelFactory = ShoppingViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ShoppingViewModel::class.java)
        val adapter = ShoppingAdapter()
        binding.shoppingRecyclerView.adapter = adapter
        //adapter.submitList(DataManager.ingredients)

        viewModel.ingredientRecipes.observe(viewLifecycleOwner, Observer { ingredientRecipes ->

            viewModel.cartRecipes.observe(viewLifecycleOwner, Observer { cartRecipes ->
                val list = getIngredients(ingredientRecipes, cartRecipes)
                Log.d(TAG, "onCreateView: $list")
                adapter.submitList(list)
            })
        })










        return binding.root
    }

    private fun getIngredients(
        ingredientRecipes: List<IngredientRecipe>,
        cartRecipes: List<CartRecipes>
    ): List<Ingredients> {
       // val shoppingList = HashMap<String, List<String>>()
        val shoppingList = mutableListOf<Ingredients>()
        val ingredientList = mutableListOf<String>()
        Log.d(TAG, "getIngredients: ingredient list $ingredientRecipes")
        Log.d(TAG, "getIngredients: cart list $cartRecipes")
        for (recipes in ingredientRecipes) {
            for (ingredients in cartRecipes) {
                if (recipes.label == ingredients.label) {
                    ingredientList.add(ingredients.ingredient)
                }
            }
            shoppingList.add(Ingredients(recipes.label, ingredientList.toList()))
            ingredientList.clear()

        }


        return shoppingList
    }


}

