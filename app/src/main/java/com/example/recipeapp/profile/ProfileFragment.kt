package com.example.recipeapp.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private val TAG = ProfileFragment::class.java.simpleName
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        setupToolBar()
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }

    private fun setupToolBar() {
        val navController = NavHostFragment.findNavController(this)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(
            binding.toolbarProfile,
            navController,
            appBarConfiguration
        )

        binding.toolbarProfile.setupWithNavController(navController, appBarConfiguration)


    }

}