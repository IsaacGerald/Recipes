package com.example.recipeapp.recipe

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.Injection
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.adapters.IngredientClickListener
import com.example.recipeapp.adapters.IngredientsAdapter
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.db.CartRecipes
import com.example.recipeapp.db.DomainRecipe
import com.example.recipeapp.network.asIngredientRecipe
import com.google.android.material.snackbar.Snackbar


class MealDetailFragment : Fragment(), IngredientClickListener {
    private val TAG = MealDetailFragment::class.java.simpleName
    private lateinit var binding: FragmentRecipeBinding
    private var isFavClicked = true
    private var isBookmarkClicked = true
    private lateinit var viewModel: MealDetailViewModel
    private lateinit var domainRecipe: DomainRecipe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false)
        binding.recipeToolbar.clipToOutline = true
        binding.lifecycleOwner = viewLifecycleOwner

        val repository = Injection.provideFoodRepository(this.requireContext())

        val args = MealDetailFragmentArgs.fromBundle(requireArguments()).recipe
        val viewModelFactory = MDViewModelFactory(args, repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MealDetailViewModel::class.java)
//        viewModel.getShoppingIngredientRecipe(args.label)
        val adapter = IngredientsAdapter(this)

        val recyclerView = binding.ingredientRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter


        viewModel.ingredientsRecipe.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            Log.d(TAG, "onCreateView: received ingredients $it")


        })

        viewModel.recipe.observe(viewLifecycleOwner, Observer { recipe ->
            if (recipe != null) {
                domainRecipe = recipe.copy()
                binding.recipe = recipe
                getIngredientStatus(adapter, recipe)

            }
        })


        setupToolBar()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun getIngredientStatus(adapter: IngredientsAdapter, recipe: DomainRecipe) {

        viewModel.cartIngredients.observe(viewLifecycleOwner, Observer { cartRecipes ->

            val cartIngredients = mutableListOf<CartRecipes>()
            val ingredients = mutableListOf<String>()
            for (ing in recipe.ingredientLines!!) {
                ingredients.add(ing)
            }

            val newIngredients: List<CartRecipes> = ingredients.asIngredientRecipe()
            cartIngredients.clear()
            if (!cartRecipes.isNullOrEmpty()) {
                Log.d(TAG, "getIngredientStatus before: $cartIngredients")
                for (ingr in cartRecipes) {
                    for (rec in newIngredients) {
                        if (rec.ingredient == ingr.ingredient) {
                            rec.isAddedToCart = true
                        }
                        cartIngredients.add(rec)
                    }
                }
                Log.d(TAG, "getIngredientStatus after: $cartIngredients")
                adapter.submitList(newIngredients)
            } else {
                Log.d(TAG, "getIngredientStatus after: $cartIngredients")
                adapter.submitList(newIngredients)

            }


        })

    }

    private fun setupToolBar() {
        val activity = (activity as MainActivity)
        activity.setSupportActionBar(binding.recipeToolbar)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        val navController = NavHostFragment.findNavController(this)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(
            binding.recipeToolbar,
            navController,
            appBarConfiguration
        )

        binding.recipeToolbar.setupWithNavController(navController, appBarConfiguration)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.meal_detail_menu, menu)
        initFavoriteStatus(menu)
    }

    private fun initFavoriteStatus(menu: Menu) {
        viewModel.favoriteRecipes?.observe(viewLifecycleOwner, Observer { recipe ->
            isFavClicked = if (recipe != null) {
                menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_filled_24)
                false
            } else {
                menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_24)
                true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_favorite -> {
                handleFavorites(item)
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }

    }

    private fun handleFavorites(item: MenuItem) {
        Log.d(TAG, "handleFavorites: domainRecipe $domainRecipe")
        if (isFavClicked) {
            isFavClicked = false
            item.setIcon(R.drawable.ic_favorite_filled_24)
            viewModel.addToFavorites()
            val message = "Recipe added to favorites"
            showToast(message)
        } else {
            isFavClicked = true
            item.setIcon(R.drawable.ic_favorite_24)
            viewModel.deleteFavorites(domainRecipe.label)
            val message = "Recipe removed from favorites"
            showToast(message)
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleBookMark(item: MenuItem) {
        if (isBookmarkClicked) {
            isBookmarkClicked = false
            item.setIcon(R.drawable.ic_bookmark_filled_24)
            viewModel.addToBookmarks()
            val message = "Recipe saved"
            showToast(message)
        } else {
            isBookmarkClicked = true
            item.setIcon(R.drawable.ic_bookmark_24)
            val message = "Recipe not saved"
            showToast(message)
        }

    }

    private fun handleShare() {
//        selectedRecipe?.let {
//            val shareUrl = selectedRecipe?.shareAs
//            val message = "Hey try out this new recipe! $shareUrl"
//            val shareIntent = Intent(Intent.ACTION_SEND)
//            shareIntent.type = "text/plain"
//            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
//            startActivity(Intent.createChooser(shareIntent, "Share Recipe"))
//        }


    }

    override fun onClick(isClicked: Boolean, item: CartRecipes) {


        if (isClicked) {
            item.isAddedToCart = true
            item.label = domainRecipe.label
            showSnackBar(item, isClicked)
            viewModel.addToCart(item)
            viewModel.addToIngredientRecipe(domainRecipe.asIngredientRecipe())

        } else {

            item.label = domainRecipe.label
            showSnackBar(item, isClicked)
            viewModel.removeFromCart(item.ingredient)
            removeFromIngredientRecipe(domainRecipe.label)
        }


    }

    private fun showSnackBar(item: CartRecipes, isClicked: Boolean) {
        if (isClicked) {
            Snackbar.make(
                this.requireView(),
                "${item.ingredient} is Added to Shopping List",
                Snackbar.LENGTH_SHORT
            )
                .setAction("View List", View.OnClickListener {
                    Toast.makeText(context, "navigate to ${item.label}", Toast.LENGTH_SHORT).show()
                })
                .show()
        } else {
            Snackbar.make(
                this.requireView(),
                "${item.ingredient} is Removed from  Shopping List",
                Snackbar.LENGTH_SHORT
            ).show()
        }


    }

    private fun removeFromIngredientRecipe(label: String) {

        viewModel.cartIngredients.observe(viewLifecycleOwner, Observer { cartRecipes ->
            if (cartRecipes.isNullOrEmpty()) {
                viewModel.deleteIngredientRecipe(label)
            } else return@Observer
        })


    }


}