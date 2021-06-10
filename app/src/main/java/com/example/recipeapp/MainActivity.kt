package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.recipeapp.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var connectionLiveData: ConnectionLiveData
    var isNetworkConnected = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val bottomNavView = binding.bottomNavView
        NavigationUI.setupWithNavController(bottomNavView, navController)












































//        connectionLiveData = ConnectionLiveData(this)
//        val navController = findNavController(R.id.nav_host_fragment)
//        drawer = binding.drawerLayout
//        //set up with navController to navigate to different fragments
//        NavigationUI.setupWithNavController(binding.navView, navController)
//        //set up with action bar
//        connectionLiveData.observe(this, { isNetworkAvailable ->
//            isNetworkConnected.postValue(isNetworkAvailable)
//            Log.d(TAG, "onCreate: $isNetworkConnected")
//            if (isNetworkAvailable) {
//               Snackbar.make(binding.root, "Network Connected", Snackbar.LENGTH_SHORT).show()
//
//            } else {
//              val snackBar =  Snackbar.make(binding.root, "Network DisConnected", Snackbar.LENGTH_INDEFINITE)
//                snackBar.setAction(getString(R.string.ok), View.OnClickListener {
//                    snackBar.dismiss()
//                })
//                //snackBar.show()

//            }
//
//        })


    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return NavigationUI.navigateUp(navController, drawer)
//    }

}