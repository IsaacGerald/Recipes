package com.example.recipeapp.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.Injection
import com.example.recipeapp.Injection.getViewModelFactory
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.adapters.OnProfileClickListener
import com.example.recipeapp.adapters.FavoriteAdapter
import com.example.recipeapp.constants.SPAN_COUNT
import com.example.recipeapp.databinding.FragmentFavoriteRecipeBinding
import com.example.recipeapp.db.FavoriteRecipes
import com.example.recipeapp.network.asDomainRecipe


class FavoriteFragment : Fragment() {
    private val TAG = FavoriteFragment::class.java.simpleName
    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: FragmentFavoriteRecipeBinding
    private val viewModel by viewModels<FavoriteViewModel>{getViewModelFactory(requireContext())}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_recipe, container, false)
        setupToolBar()
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = FavoriteAdapter(OnProfileClickListener {
            viewModel.navigateToSelectedMeal(it)
            Toast.makeText(context, "${it.label} is clicked!", Toast.LENGTH_SHORT).show()
        })

        binding.recyclerViewFavorite.layoutManager =
            GridLayoutManager(this.requireContext(), SPAN_COUNT)
        binding.recyclerViewFavorite.adapter = adapter

        viewModel.getFavRecipes?.observe(viewLifecycleOwner, Observer { favoriteRecipes: List<FavoriteRecipes> ->
            Log.d(TAG, "onCreateView: Favorite fragment $favoriteRecipes")
            adapter.submitList(favoriteRecipes)
        })

        navigateToSelectedMeal()


        return binding.root
    }

    private fun navigateToSelectedMeal() {
        viewModel.navigateToMeal.observe(viewLifecycleOwner, Observer { favoriteRecipe ->
            favoriteRecipe?.let {
                val recipe = favoriteRecipe.asDomainRecipe()
                findNavController().navigate(
                    FavoriteFragmentDirections.actionFavoriteRecipeFragmentToRecipeFragment(
                        recipe
                    )
                )
                viewModel.navigationCompleted()
            }


        })
    }

    private fun setupToolBar() {
        val navController = NavHostFragment.findNavController(this)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(
            binding.favoriteToolbar,
            navController,
            appBarConfiguration
        )

        binding.favoriteToolbar.setupWithNavController(navController, appBarConfiguration)


    }


}