package com.example.recipeapp.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.Injection.getViewModelFactory
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.adapters.*
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.network.FoodApiTypes
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.chips_layout.view.*


class HomeFragment : Fragment() {
    private val TAG = HomeFragment::class.java.simpleName

    private lateinit var binding: FragmentHomeBinding
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: DishAdapter
    private var chipText: String? = null
    val viewModel by viewModels<HomeViewModel> { getViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        toolbar = binding.homeToolbar
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupToolBar()
        setHasOptionsMenu(true)
        bindMeals()
        setChips()
        navigateToMeal()

        viewModel.fetchResponse.observe(viewLifecycleOwner, Observer { response ->

            if (response.isSuccessful) {
                if (response.body()?.hits.isNullOrEmpty()) {
                    showToast(response.message().toString())
                    stopShimmer()
                }

            } else {
                showToast(response.message().toString())
                stopShimmer()
            }
        })

        viewModel.queryString.observe(viewLifecycleOwner, Observer { query ->
            if (query != null) {
                chipText = query
                getRecipes(chipText!!)
            }

        })

        if (chipText == null) {
            populateRecyclerView(FoodApiTypes.MAIN_COURSE.value)
            viewModel.setSearchQuery(FoodApiTypes.MAIN_COURSE.value)
        }



        return binding.root
    }

    private fun navigateToMeal() {
        viewModel.navigateToSelectedMeal.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(
                        it
                    )
                )
                viewModel.displayMealCompleted()
            }

        })
    }


    private fun stopShimmer() {
        binding.shimmerFrameLayout.stopShimmerAnimation()
        binding.recyclerViewDish.visibility = View.VISIBLE
        binding.shimmerFrameLayout.visibility = View.GONE

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setChips() {
        val view = binding.chipsIncludeLayout
        val chipGroup = view.chipGroup

        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            chip?.let {
                //Toast.makeText(requireContext(), chip.text, Toast.LENGTH_SHORT).show()
                val mealType = chip.text.toString()
                viewModel.setSearchQuery(mealType)
                populateRecyclerView(chip.text)


            }
        }
    }

    private fun populateRecyclerView(text: CharSequence?) {
        if (text.toString().isNotEmpty()) {
            startShimmer()
            viewModel.fetchRecipe(text.toString())
            getRecipes(text.toString())
        }


    }

    private fun getRecipes(text: String) {
        viewModel.getSearchRecipeFromDatabase(text)
            .observe(viewLifecycleOwner, {
                if (!it.isNullOrEmpty()) {
                    adapter.submitList(it)
                    stopShimmer()
                }
            })
    }


    private fun startShimmer() {
        binding.shimmerFrameLayout.startShimmerAnimation()
        binding.recyclerViewDish.visibility = View.GONE
        binding.shimmerFrameLayout.visibility = View.VISIBLE

    }

    private fun setupToolBar() {
        (activity as MainActivity).setSupportActionBar(toolbar)
        val navController = NavHostFragment.findNavController(this)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.homeToolbar, navController, appBarConfiguration)

        binding.homeToolbar.setupWithNavController(navController, appBarConfiguration)
        //setUpToggle()


    }


    private fun bindMeals() {
        binding.recyclerViewDish.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        adapter = DishAdapter(DishItemClickListener { recipe ->
            recipe.let {
                viewModel.displayMeal(recipe)
            }

        }, FavItemClickListener { recipe ->
            viewModel.addToFavorites(recipe)
        })
        binding.recyclerViewDish.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_options_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) ||
                super.onOptionsItemSelected(item)
    }

}


//        private fun setSliderView() {
//        val adapter = SliderAdapter(DataManager.sliderList.toList())
//        val sliderView = binding.imageSlider
//        sliderView.setSliderAdapter(adapter)
//        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
//        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
//        sliderView.startAutoCycle()
//    }

//    private fun setUpToggle() {
//        val activity = (activity as MainActivity)
//        val drawer = activity.findViewById<DrawerLayout>(R.id.drawer_layout)
//        val drawable = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_menu_24)
//        val toggle = ActionBarDrawerToggle(
//            this.activity, drawer, toolbar, R.string.open_nav_drawer,
//            R.string.close_nav_drawer
//        )
//
//        drawer.addDrawerListener(toggle)
//        toggle.syncState()
//
//        toggle.toolbarNavigationClickListener = View.OnClickListener {
//            drawer.openDrawer(GravityCompat.START)
//        }
//
//        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        toolbar.navigationIcon = drawable
//
//
//    }

