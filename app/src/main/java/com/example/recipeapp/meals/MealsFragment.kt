package com.example.recipeapp.meals

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.ConnectionLiveData
import com.example.recipeapp.Injection
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.adapters.MealAdapter
import com.example.recipeapp.adapters.OnclickListener
import com.example.recipeapp.databinding.MealListFragmentBinding
import com.example.recipeapp.db.DomainRecipe
import kotlinx.coroutines.*


class MealsFragment : Fragment() {
    private val TAG = MealsFragment::class.java.simpleName
    private lateinit var viewModel: MealsViewModel
    private var searchJob: Job? = null
    private lateinit var connectionLiveData: ConnectionLiveData
    private var isNetworkConnected: Boolean? = false
    private lateinit var binding: MealListFragmentBinding
    private lateinit var adapter: MealAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(this.requireContext(), R.color.primaryColor)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.meal_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setupToolBar(binding)

        val argsId = MealsFragmentArgs.fromBundle(requireArguments()).id
        val argsName = MealsFragmentArgs.fromBundle(requireArguments()).modelName
        val repository = Injection.provideFoodRepository(this.requireContext())

        val viewModelFactory =
            MealsViewModelFactory(
                id = argsId,
                modelName = argsName,
                repository = repository
            )
        viewModel = ViewModelProvider(this, viewModelFactory).get(MealsViewModel::class.java)


        init()

        viewModel.mealResponse().observe(viewLifecycleOwner, Observer { recipes: List<DomainRecipe> ->

            if (!recipes.isNullOrEmpty()) {

                binding.linearNetworkLayout.root.visibility = View.GONE
                binding.mealProgressBar.visibility = View.GONE
                adapter.submitList(recipes)
            }


        })

        viewModel.response.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })


        navigateToSelectedMeal()
        displayImage()
        displayName()
        displayResponseState()
        handleRetryNetworkButton()

        return binding.root
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun init() {
        binding.recyclerViewMeals.layoutManager = LinearLayoutManager(this.context)
        adapter = MealAdapter(OnclickListener { recipe ->
            viewModel.displayDetailMeal(recipe)

        })
        binding.recyclerViewMeals.adapter = adapter
    }

    private fun handleRetryNetworkButton() {
        val networkLayout = binding.linearNetworkLayout
        val retryButton = networkLayout.retryButton
        retryButton.setOnClickListener {
                viewModel.refreshFoodRecipe()
            binding.mealProgressBar.visibility = View.VISIBLE
        }
    }

    private fun displayResponseState() {
        viewModel.displayResponse.observe(viewLifecycleOwner, Observer {
            binding.mealProgressBar.visibility = View.GONE
            binding.linearNetworkLayout.root.visibility = View.VISIBLE
        })
    }

    private fun displayName() {
        viewModel.displayName.observe(viewLifecycleOwner, Observer { name ->
            binding.collapsingToolbar.title = name
        })
    }

    private fun displayImage() {
        viewModel.displayImage.observe(viewLifecycleOwner, Observer { image ->
            if (image != null) {
                val imageView = binding.mealsImageView
                Glide.with(imageView)
                    .load(image)
                    .into(imageView)
            }
        })
    }

    private fun navigateToSelectedMeal() {
        viewModel.navigateToSelectedMeal.observe(viewLifecycleOwner, Observer { recipe ->
            if (null != recipe) {
                this.findNavController().navigate(
                    MealsFragmentDirections.actionMealsFragmentToRecipeFragment(recipe)
                )
                viewModel.displayDetailMealComplete()
            }


        })
    }

    private fun setupToolBar(binding: MealListFragmentBinding) {
        (activity as AppCompatActivity).setSupportActionBar(binding.mealListToolbar)
        setHasOptionsMenu(true)
        val navController = NavHostFragment.findNavController(this)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.mealListToolbar, navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.meals_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) ||
                super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(this.requireContext(), R.color.white_variation)
        super.onDestroy()
    }
}