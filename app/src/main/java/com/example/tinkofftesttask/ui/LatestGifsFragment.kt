package com.example.tinkofftesttask.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.tinkofftesttask.R
import com.example.tinkofftesttask.databinding.FragmentLatestGifsBinding
import com.example.tinkofftesttask.presentation.GifsEvent
import com.example.tinkofftesttask.presentation.LatestGifsViewModel
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
    ): View? {
        _binding = FragmentLatestGifsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                latestGifsViewModel.gifsFlow.collectLatest { event ->
                    when (event) {
                        is GifsEvent.Error -> {
                            Toast.makeText(
                                context,
                                event.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is GifsEvent.Loading -> {
                            Toast.makeText(
                                context,
                                "Loading",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is GifsEvent.Success -> {
                            context?.let {
                                Glide.with(it)
                                    .asGif()
                                    .load(event.gifs[3].gifURL)
                                    .into(binding.cardImage);
                            }
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