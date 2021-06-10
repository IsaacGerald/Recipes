package com.example.recipeapp.mealdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import androidx.fragment.app.Fragment
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.recipeapp.Injection
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentMealDetailsBinding
import com.example.recipeapp.db.DomainRecipe
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
import kotlinx.android.synthetic.main.fragment_meal_details.*


class RecipeFragment : Fragment() {
    private lateinit var viewModel: RecipeViewModel
    private var selectedRecipe: DomainRecipe? = null
    private lateinit var binding: FragmentMealDetailsBinding
    private var isFavClicked = true
    private var isBookmarkClicked = true
    private var url: String? = null
    private var domainRecipe: DomainRecipe? = null
    private lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_meal_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        (activity as AppCompatActivity).setSupportActionBar(binding.mealDetailsToolbar)
        setHasOptionsMenu(true)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.mealDetailsToolbar.setupWithNavController(navController, appBarConfiguration)

        binding.mealDetailsToolbar


        val app = requireNotNull(activity).application
        val args = RecipeFragmentArgs.fromBundle(requireArguments()).recipe
        domainRecipe = args.copy()
        val isNetworkAvailable = (activity as MainActivity).isNetworkConnected
        val repository = Injection.provideFoodRepository(this.requireContext())

        val viewModelFactory = RecipeViewModelFactory(
            args,
            repository
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java)

        val web = binding.webView
        progressBar = binding.spinKit
        val sprite: Sprite = Circle()
        progressBar.indeterminateDrawable = sprite


        viewModel.selectedRecipe.observe(viewLifecycleOwner, Observer { recipe ->
            if (recipe != null) {
                selectedRecipe = recipe
                url = recipe.url
                initWebView(web)

            }
        })
//
//        val refreshLayout = binding.swipeRefreshLayout
//        if(refreshLayout.isRefreshing){
//            binding.swipeRefreshLayout.isRefreshing = false
//        }

//        binding.swipeRefreshLayout.setOnRefreshListener {
//            Handler(Looper.getMainLooper()).postDelayed(Runnable {
//                initWebView(web)
//                binding.swipeRefreshLayout.isRefreshing = false
//            }, 2000)
//        }


        return binding.root
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(web: WebView) {
        web.apply {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            this.webChromeClient = WebChromeClient()
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true
            }

        }
        if (url != null) {
            web.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                }

            }

            web.loadUrl(url!!)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_mealDetails -> {
                moveToMealDetails()
            }
            R.id.action_favorite -> {
                handleFavorites(item)
                true
            }
            else ->
                NavigationUI.onNavDestinationSelected(
                    item,
                    requireView().findNavController()
                ) || super.onOptionsItemSelected(item)
        }

    }

    private fun moveToMealDetails(): Boolean {
        if (domainRecipe != null){
            findNavController().navigate(RecipeFragmentDirections.actionMealDetailsFragmentToRecipeFragment(domainRecipe!!))
            return true
        }

        return false


    }

    private fun handleShare() {
        selectedRecipe?.let {
            val shareUrl = selectedRecipe?.shareAs
            val message = "Hey try out this new recipe! $shareUrl"
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(shareIntent, "Share Recipe"))
        }


    }

    private fun handleFavorites(item: MenuItem) {

        if (isFavClicked) {
            isFavClicked = false
            item.setIcon(R.drawable.ic_favorite_filled_24)
            viewModel.addToFavorites()
            val message = "Recipe added to favorites"
            showToast(message)
        } else {
            isFavClicked = true
            item.setIcon(R.drawable.ic_favorite_24)
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
}

