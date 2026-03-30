package com.example.sem8firebaseauth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Enable modern edge-to-edge display
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)

        // 2. Adjust padding for Status/Navigation bars
        val mainLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main_layout_id)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        // --- SEARCH LOGIC ---
        val etSearch = findViewById<EditText>(R.id.etSearch)
        etSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()
                if (query.isNotEmpty()) {
                    Toast.makeText(this, "Searching for: $query", Toast.LENGTH_SHORT).show()
                    hideKeyboard()
                }
                true
            } else false
        }

        // --- LOCATION BAR LOGIC ---
        val locationBar = findViewById<LinearLayout>(R.id.locationBar)
        locationBar.setOnClickListener {
            Toast.makeText(this, "Deliver to selection opened", Toast.LENGTH_SHORT).show()
        }

        // --- BOTTOM NAVIGATION LOGIC ---
        val navHome = findViewById<ImageView>(R.id.nav_home)
        val navProfile = findViewById<ImageView>(R.id.nav_profile)
        val navCart = findViewById<ImageView>(R.id.nav_cart)
        val navMenu = findViewById<ImageView>(R.id.nav_menu)

        val navItems = listOf(navHome, navProfile, navCart, navMenu)

        navItems.forEach { icon ->
            icon.setOnClickListener {
                // Reset all to gray, then highlight the clicked one to Amazon Teal
                navItems.forEach { it.setColorFilter(Color.parseColor("#555555")) }
                (it as ImageView).setColorFilter(Color.parseColor("#007185"))

                Toast.makeText(this, "Navigating to: ${it.contentDescription ?: "Section"}", Toast.LENGTH_SHORT).show()
            }
        }

        // --- LOGOUT LOGIC ---
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Signing Out...", Toast.LENGTH_LONG).show()
            // Logic to clear user session (e.g. FirebaseAuth.getInstance().signOut())
            finish()
        }
    }

    // Helper function to hide keyboard after search
    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}