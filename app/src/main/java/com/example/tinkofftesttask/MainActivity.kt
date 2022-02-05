package com.example.tinkofftesttask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tinkofftesttask.databinding.ActivityMainBinding
import com.example.tinkofftesttask.ui.TabsAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val tabsAdapter = TabsAdapter(supportFragmentManager, lifecycle)

        binding.viewPager.adapter = tabsAdapter

        TabLayoutMediator(binding.tabsGif, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Latest"
                }
                1 -> {
                    tab.text = "Top"
                }
                2 -> {
                    tab.text = "Hot"
                }
            }
        }.attach()
    }

    override fun onBackPressed() {
        if (binding.viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }
    }

}