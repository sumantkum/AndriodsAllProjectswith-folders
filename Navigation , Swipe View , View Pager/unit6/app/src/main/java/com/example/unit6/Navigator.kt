package com.example.unit6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class Navigator : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigator)

        // Initialize views
        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigation)
        toolbar = findViewById(R.id.toolbar) // Make sure you have this ID in your XML

        // Step 1: Set up the Toolbar
        setSupportActionBar(toolbar)

        // Step 2: Create AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home), // Top-level destinations
            drawerLayout
        )

        // Step 3: Connect Navigation Controller with Toolbar
        val navController = findNavController(R.id.frame)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Step 4: Connect NavigationView with NavController
        navigationView.setupWithNavController(navController)

        // Handle navigation item clicks (alternative approach)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, HomeFragment()).commit()
                }
                R.id.nav_gallery -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, GalleryFragment()).commit()
                }
                R.id.nav_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, SettingFragment()).commit()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    // Step 5: Handle navigation up button
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.frame)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Step 6: Handle back button press
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}