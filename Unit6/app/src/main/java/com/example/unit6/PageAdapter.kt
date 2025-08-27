package com.example.unit6

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = listOf(
        MainFragment(),
        SecondFragment(),
        thirdFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getFragmentTitle(position: Int): String {
        return when (position) {
            0 -> "Main"
            1 -> "Second"
            2 -> "Third"
            else -> ""
        }
    }

    fun getFragmentIcon(position: Int): Int {
        return when (position) {
            0 -> R.drawable.ic_home
            1 -> R.drawable.ic_favorite
            2 -> R.drawable.ic_settings
            else -> 0
        }
    }

    fun getFragmentDescription(position: Int): String {
        return when (position) {
            0 -> "This is the main fragment"
            1 -> "This is the second fragment"
            2 -> "This is the third fragment"
            else -> ""
        }
    }
}
