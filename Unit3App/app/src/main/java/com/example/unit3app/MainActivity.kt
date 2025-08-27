package com.example.unit3app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.unit3app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button click listeners to load respective fragments
        binding.buttonFragment1.setOnClickListener {
            goToFragment(Fragment1())
        }

        binding.buttonFragment2.setOnClickListener {
            goToFragment(Fragment2())  // Fixed typo here (Fragament2 -> Fragment2)
        }
    }

    private fun goToFragment(fragment: Fragment) {
        // Using supportFragmentManager directly
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
