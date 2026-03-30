package com.example.unit3sem7

import android.R
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var btnOpenDrawer: Button
    private lateinit var tvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        drawerLayout = findViewById(R.id.drawerLayout)
//        navigationView = findViewById(R.id.navigationView)
//        btnOpenDrawer = findViewById(R.id.btnOpenDrawer)
//        tvTitle = findViewById(R.id.tvTitle)
//
//        // Open Drawer button
//        btnOpenDrawer.setOnClickListener {
//            drawerLayout.openDrawer(GravityCompat.START)
//        }
//
//        // Handle menu item clicks
//        navigationView.setNavigationItemSelectedListener { menuItem ->
//            handleNavigation(menuItem)
//            true
//        }
//    }
//
//    private fun handleNavigation(menuItem: MenuItem) {
//        when (menuItem.itemId) {
//            R.id.nav_home -> tvTitle.text = "🏠 Home Screen"
//            R.id.nav_about -> tvTitle.text = "ℹ️ About Screen"
//            R.id.nav_gallery -> tvTitle.text = "🖼️ Gallery Screen"
//        }
//        drawerLayout.closeDrawer(GravityCompat.START)
    }
}
