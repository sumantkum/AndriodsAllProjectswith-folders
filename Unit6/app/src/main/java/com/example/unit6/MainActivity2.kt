package com.example.unit6

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity2 : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        // Initialize views
        fab = findViewById(R.id.fab)
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)

        // Set up toolbar
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Load default fragment
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Bottom navigation listener
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_library -> replaceFragment(LibraryFragment())
                R.id.nav_graph -> replaceFragment(ShortsFragment())
                R.id.nav_subscriptions -> replaceFragment(SubscriptionFragment())
                else -> false
            }
        }

        // FAB click listener
        fab.setOnClickListener {
            showBottomDialog()
        }
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        return true
    }

    private fun showBottomDialog() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottomsheetlayout)

        val videoLayout = dialog.findViewById<LinearLayout>(R.id.layoutVideo)
        val shortsLayout = dialog.findViewById<LinearLayout>(R.id.layoutShorts)
        val liveLayout = dialog.findViewById<LinearLayout>(R.id.layoutLive)
        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)

        videoLayout?.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, "Upload a Video is clicked", Toast.LENGTH_SHORT).show()
        }

        shortsLayout?.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, "Create a Short is clicked", Toast.LENGTH_SHORT).show()
        }

        liveLayout?.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, "Go Live is clicked", Toast.LENGTH_SHORT).show()
        }

        cancelButton?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.BOTTOM)

    }

}
