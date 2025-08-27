package com.example.unit6

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.unit6.databinding.ActivityMyBinding
import com.google.android.material.tabs.TabLayout

class MyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyBinding
    private lateinit var adapter: PageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PageAdapter(this)
        binding.viewPager2.adapter = adapter

        // Add tabs dynamically using adapter's info
        for (i in 0 until adapter.itemCount) {
            val tab = binding.tabLayout.newTab()
            tab.text = adapter.getFragmentTitle(i)
            tab.setIcon(adapter.getFragmentIcon(i))
            binding.tabLayout.addTab(tab)
        }

        // Sync TabLayout with ViewPager2
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager2.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

}