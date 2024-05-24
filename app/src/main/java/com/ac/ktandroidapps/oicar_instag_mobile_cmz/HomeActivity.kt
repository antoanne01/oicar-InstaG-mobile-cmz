package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters.MyPostRvAdapter
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)

    }
}