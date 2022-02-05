package com.example.tinkofftesttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.tinkofftesttask.R
import com.example.tinkofftesttask.databinding.FragmentHotGifsBinding
import com.example.tinkofftesttask.presentation.ConnectivityViewModel
import com.example.tinkofftesttask.presentation.HotGifsViewModel
import com.example.tinkofftesttask.ui.adapter.PagingAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HotGifsFragment : Fragment(R.layout.fragment_hot_gifs) {

    private var _binding: FragmentHotGifsBinding? = null
    private val binding get() = _binding!!
    private val hotGifsViewModel by viewModels<HotGifsViewModel>()
    private val connectivityViewModel by activityViewModels<ConnectivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotGifsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAdapter = PagingAdapter()
        binding.viewPager.adapter = pagingAdapter

        binding.apply {
            btnNext.setOnClickListener {
                val currentPosition = binding.viewPager.currentItem
                binding.viewPager.currentItem = currentPosition + 1
            }
            btnPrev.setOnClickListener {
                val currentPosition = binding.viewPager.currentItem
                binding.viewPager.currentItem = currentPosition - 1
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    connectivityViewModel.hasInternet.collectLatest { hasInternet ->
                        if (!hasInternet) {
                            Snackbar.make(
                                binding.root,
                                "No internet connection",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                launch {
                    hotGifsViewModel.gifs.collectLatest {
                        pagingAdapter.submitData(it)
                    }
                }
                launch {
                    pagingAdapter.loadStateFlow.collectLatest { loadState ->
                        val isListEmpty =
                            loadState.refresh is LoadState.Error && pagingAdapter.itemCount == 0

                        if (pagingAdapter.itemCount == 0) {
                            binding.btnPrev.isVisible = false
                        }


                        binding.apply {
                            viewPager.isVisible =
                                loadState.refresh is LoadState.NotLoading
                            btnNext.isVisible = loadState.refresh is LoadState.NotLoading
                            btnRefresh.isVisible = loadState.refresh is LoadState.NotLoading
                            progressBar.isVisible = loadState.refresh is LoadState.Loading
                        }

                        val errorState = loadState.prepend as? LoadState.Error
                            ?: loadState.append as? LoadState.Error
                            ?: loadState.refresh as? LoadState.Error

                        errorState?.let {
                            Toast.makeText(
                                context,
                                "\uD83D\uDE28 Whoops ${it.error}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
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