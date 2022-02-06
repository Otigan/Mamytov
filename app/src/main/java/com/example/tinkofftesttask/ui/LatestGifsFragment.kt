package com.example.tinkofftesttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.viewpager2.widget.ViewPager2
import com.example.tinkofftesttask.R
import com.example.tinkofftesttask.databinding.FragmentLatestGifsBinding
import com.example.tinkofftesttask.presentation.LatestGifsViewModel
import com.example.tinkofftesttask.ui.adapter.PagingAdapter
import com.example.tinkofftesttask.ui.adapter.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LatestGifsFragment : Fragment(R.layout.fragment_latest_gifs) {

    private var _binding: FragmentLatestGifsBinding? = null
    private val binding get() = _binding!!
    private val latestGifsViewModel by viewModels<LatestGifsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLatestGifsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAdapter = PagingAdapter()

        binding.apply {

            viewPager.adapter = pagingAdapter
            btnNext.setOnClickListener {
                val currentPosition = binding.viewPager.currentItem
                binding.viewPager.currentItem = currentPosition + 1
            }
            btnPrev.setOnClickListener {
                val currentPosition = binding.viewPager.currentItem
                binding.viewPager.currentItem = currentPosition - 1
            }
            btnRetry.setOnClickListener {
                pagingAdapter.refresh()
            }

            viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT

            viewPager.setPageTransformer(ZoomOutPageTransformer())
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.btnPrev.isVisible = position != 0
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    pagingAdapter.loadStateFlow.collectLatest { loadState ->
                        val isListEmpty =
                            loadState.refresh is LoadState.Error && pagingAdapter.itemCount == 0

                        binding.apply {
                            viewPager.isVisible =
                                loadState.refresh is LoadState.NotLoading
                            btnNext.isVisible = loadState.refresh is LoadState.NotLoading
                            btnRefresh.isVisible = loadState.refresh is LoadState.NotLoading
                            btnPrev.isVisible =
                                viewPager.currentItem != 0
                            progressBar.isVisible = loadState.refresh is LoadState.Loading
                            btnRetry.isVisible = loadState.refresh is LoadState.Error
                            txtViewError.isVisible = loadState.refresh is LoadState.Error
                            imgError.isVisible = loadState.refresh is LoadState.Error
                        }

                        val errorState = loadState.prepend as? LoadState.Error
                            ?: loadState.append as? LoadState.Error
                            ?: loadState.refresh as? LoadState.Error

                        errorState?.let {
                            binding.txtViewError.text = getString(R.string.loading_error)
                            binding.imgError.setImageResource(R.drawable.unknown_error)
                        }
                    }
                }
                launch {
                    latestGifsViewModel.gifs.collectLatest {
                        pagingAdapter.submitData(it)
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}