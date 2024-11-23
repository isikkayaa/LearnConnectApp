package com.example.learnconnectapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.learnconnectapp.databinding.ActivityMainBinding
import com.example.learnconnectapp.room.Veritabani
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var firebaseAuth : FirebaseAuth


    private lateinit var vt :Veritabani

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        vt = Veritabani.veritabaniErisim(this)!!



        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = binding.drawerLayout
        setBottomNavigation()
        setNavigationDrawer()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setBottomNavigation() {
        bottomNavigationView = binding.bottomNavigationView
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in listOf(R.id.signUpFragment, R.id.loginFragment)) {
                binding.toolbar.visibility = View.GONE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            } else {
                binding.toolbar.visibility = View.VISIBLE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                binding.toolbar.title = when (destination.id) {
                    R.id.courseDetailFragment -> getString(R.string.course_detail)
                    else -> ""
                }

                bottomNavigationView.visibility = if (destination.id in listOf(R.id.signUpFragment, R.id.loginFragment)) {
                    View.GONE
                } else {
                    View.VISIBLE
                }


            }


        }}


    private fun setNavigationDrawer() {

        val navigationView = binding.navigationView
        val logoutItem = navigationView.menu.findItem(R.id.logout_item)


        navigationView.post {
            val logoutView = navigationView.findViewById<View>(R.id.logout_item)
            logoutView?.setBackgroundColor(ContextCompat.getColor(this, R.color.anaRenk))
        }

        val themeSwitch = navigationView.menu.findItem(R.id.themeSwitch).actionView as Switch
        themeSwitch.isChecked = (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homePageFragment ||
                destination.id == R.id.favouritesFragment2 ||
                destination.id == R.id.profileFragment2 ||
                destination.id == R.id.downloadFragment2

            ) {

                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                actionBarDrawerToggle.isDrawerIndicatorEnabled = true
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                actionBarDrawerToggle.syncState()
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                actionBarDrawerToggle.isDrawerIndicatorEnabled = false
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }


        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.baseline_menu_24)


        // Navigation Item Listener
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.logout_item -> showLogoutConfirmationDialog()
            }
            true
        }
    }








    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }


    private fun showLogoutConfirmationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.logout_confirm))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> logout() }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
            .show()
    }
    private fun logout() {
        FirebaseAuth.getInstance().signOut()

        navController.navigate(R.id.loginFragment)
    }

    override fun onStart() {
        super.onStart()
        actionBarDrawerToggle.syncState()
    }
}