package com.example.recipeapp.ui.search

import android.annotation.SuppressLint
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.recipeapp.Injection
import com.example.recipeapp.R
import com.example.recipeapp.adapters.*
import com.example.recipeapp.constants.QUERY
import com.example.recipeapp.databinding.FragmentSearchBinding
import com.example.recipeapp.db.SearchRecipes
import com.example.recipeapp.network.asDomainRecipe
import com.example.recipeapp.network.asSearchRecipes
import com.example.recipeapp.utils.ImagePreviewerUtils.getBlurredScreenDrawable
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment(), OnTextClickListener, OnImageClickListener {
    private val TAG = SearchFragment::class.java.simpleName
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapterRecent: RecentSearchAdapter
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var binding: FragmentSearchBinding
    private var queryString: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        if (savedInstanceState != null) {
            queryString = savedInstanceState.getString(QUERY)

        }

        val repository = Injection.provideFoodRepository(this.requireContext())
        val viewModelFactory = SearchViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        setupSearchToolbar()
        setUpSearchedRecipes()
        setUpRecentSearch()

        binding.searchRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())


        viewModel.selectedRecipe.observe(viewLifecycleOwner, Observer { recipe ->
            if (recipe != null) {
                val domainRecipe = recipe.asDomainRecipe()
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragment2ToMealDetailsFragment(
                        domainRecipe
                    )
                )
                viewModel.displaySearchRecipeCompleted()
            }

        })

        viewModel.networkResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }

        })

        viewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                showToast(it)
            }

        })

        viewModel.isSearchViewVisible.observe(viewLifecycleOwner, Observer { isVisible ->
            if (isVisible) {
                initSearchRecipes()
            } else {
                initRecentSearch()
            }
        })


        handleSearch()
        refreshLayout()




        return binding.root
    }

    private fun refreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            setUpRecentSearch()
            initRecentSearch()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
            }, 2000)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isAttached = binding.materialSearch.isAttachedToWindow
        if (isAttached) {
            if (!binding.materialSearch.isSearchOpen) {
                binding.materialSearch.showSearch()
            }
        }
    }


    private fun handleSearch() {
        val searchView = binding.materialSearch
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    showProgressBar()
                    queryString = query
                    viewModel.searchRecipe(query)
                    viewModel.searchResponse.observe(viewLifecycleOwner, Observer { response ->
                        if (response.isSuccessful) {
                            if (!response.body()?.hits.isNullOrEmpty()) {
                                viewModel.displaySearchView()
                            } else {
                                hideProgressBar()
                                showNoResult()
                                binding.txtNoResults.text = "No results for '$query'"
                            }

                        } else {
                            showToast(response.message())
                        }
                    })

                }
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {
                    if (newText.isEmpty()) {
                        showRecyclerView()
                        viewModel.hideSearchView()
                    } else {
                        if (queryString != newText) {
                            showRecyclerView()
                            viewModel.clearRecipes()
                            viewModel.displaySearchView()
                            viewModel.getSearchRecentRecipe(newText)
                                .observe(viewLifecycleOwner, Observer {
                                    val searchRecipe = it
                                    val newList = mutableListOf<SearchRecipes>()
                                    if (!searchRecipe.isNullOrEmpty()) {
                                        for (list in searchRecipe) {
                                            if (list.label!!.contains(newText)) {
                                                newList.add(list)
                                            }
                                        }
                                    }
                                    searchAdapter.submitList(searchRecipe)
                                })
                        } else {
                            viewModel.displaySearchView()
                        }
                    }
                }

                return true
            }

        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(QUERY, queryString)
        super.onSaveInstanceState(outState)
    }

    private fun showProgressBar() {
        binding.searchProgressBar.visibility = View.VISIBLE
        binding.searchRecyclerView.visibility = View.GONE
    }

    private fun hideProgressBar() {
        binding.searchProgressBar.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.VISIBLE
    }

    private fun setupSearchToolbar() {

        (activity as AppCompatActivity).setSupportActionBar(binding.searchToolbar)
        val navController = NavHostFragment.findNavController(this)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(
            binding.searchToolbar,
            navController,
            appBarConfiguration
        )
        binding.searchToolbar.setupWithNavController(navController, appBarConfiguration)
        setHasOptionsMenu(true)

    }

    private fun showNoResult() {
        binding.txtNoResults.visibility = View.VISIBLE
        binding.searchRecyclerView.visibility = View.GONE
    }

    private fun showRecyclerView() {
        binding.txtNoResults.visibility = View.GONE
        binding.searchRecyclerView.visibility = View.VISIBLE
    }

    private fun initSearchRecipes() {

        binding.searchRecyclerView.adapter = searchAdapter
        if (queryString != null) {
            viewModel.searchedRecipe(queryString!!).observe(viewLifecycleOwner, Observer {
                if (!it.isNullOrEmpty()) {
                    hideProgressBar()
                    searchAdapter.submitList(it.asSearchRecipes())
                }
            })

        }


    }

    private fun setUpSearchedRecipes() {
        searchAdapter = SearchAdapter(OnSearchClickListener { recipe ->
            Log.d(TAG, "onCreateView new search recipe ${recipe.label} is clicked ")
            viewModel.displaySearchRecipe(recipe)
            viewModel.insertSearchRecipe(recipe)
        })
    }

    private fun initRecentSearch() {

        binding.searchRecyclerView.adapter = adapterRecent
        viewModel.recentlySearchedRecipes.observe(viewLifecycleOwner, Observer { recentRecipes ->
            Log.d(TAG, "onCreateView: fetching recent recipes...")
            if (!recentRecipes.isNullOrEmpty()) {
                adapterRecent.addHeaderAndSubmitList(recentRecipes)
            }
        })

    }

    private fun setUpRecentSearch() {
        adapterRecent = RecentSearchAdapter(OnSearchClickListener { recipe ->
            Log.d(TAG, "onCreateView recent search recipe ${recipe.label} is clicked ")
            viewModel.displaySearchRecipe(recipe)

        }, this, this, OnDeleteClickListener { recipe: SearchRecipes ->
            viewModel.deleteRecipe(recipe.id)
        })
    }


    override fun onClickListener() {
        Toast.makeText(context, "Cleared all recent recipes", Toast.LENGTH_SHORT).show()
        viewModel.clearAllRecentRecipes()

    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        (activity as AppCompatActivity).setSupportActionBar(null)
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        binding.materialSearch.setMenuItem(item)

    }

    override fun onImageClickListener(item: SearchRecipes, view: View) {
        showImagePreview(item, view)
    }

    private fun showImagePreview(item: SearchRecipes, view: View) {

        val imageUrl = Uri.parse(item.image)
        val background = getBlurredScreenDrawable(requireContext(), view.rootView)
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.preview_image_layout, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.preview_image)

        Glide.with(imageView).load(imageUrl)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            )
            .into(imageView)


        val dialog = Dialog(requireContext(), R.style.ImagePreviewerTheme)
        dialog.window!!.setBackgroundDrawable(background)
        dialog.setContentView(dialogView)
        dialog.show()

        view.setOnTouchListener(object : View.OnTouchListener {
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (dialog.isShowing) {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    val action = event.actionMasked
                    if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                        dialog.dismiss()
                        return true
                    }
                }
                return false
            }
        })

    }


}