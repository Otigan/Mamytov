package com.example.tinkofftesttask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
import com.example.tinkofftesttask.databinding.ActivityMainBinding
import com.example.tinkofftesttask.ui.adapter.TabsAdapter
import com.example.tinkofftesttask.ui.adapter.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val tabsAdapter = TabsAdapter(supportFragmentManager, lifecycle)

        binding.apply {
            viewPager.adapter = tabsAdapter
            viewPager.offscreenPageLimit = OFFSCREEN_PAGE_LIMIT_DEFAULT
            viewPager.setPageTransformer(ZoomOutPageTransformer())
        }

        TabLayoutMediator(binding.tabsGif, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = resources.getString(R.string.tab_latest)
                }
                1 -> {
                    tab.text = resources.getString(R.string.tab_top)
                }
                2 -> {
                    tab.text = resources.getString(R.string.tab_hot)
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