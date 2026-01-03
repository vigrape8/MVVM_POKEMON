package com.example.pokemon

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController

import com.example.pokemon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        appBarConfiguration= AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfiguration)
    }
    //estrella
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fav, menu)
        return true
    }
    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        return onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }
    //boton de devolver
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}