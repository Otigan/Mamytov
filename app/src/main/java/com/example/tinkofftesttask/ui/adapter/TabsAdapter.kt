package com.example.tinkofftesttask.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tinkofftesttask.ui.HotGifsFragment
import com.example.tinkofftesttask.ui.LatestGifsFragment
import com.example.tinkofftesttask.ui.TopGifsFragment

class TabsAdapter(supportFragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(supportFragmentManager, lifecycle) {


    override fun getItemCount(): Int = 3


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                LatestGifsFragment()
            }
            1 -> {
                TopGifsFragment()
            }
            2 -> {
                HotGifsFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}