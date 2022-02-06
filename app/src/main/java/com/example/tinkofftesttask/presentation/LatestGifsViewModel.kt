package com.example.tinkofftesttask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.tinkofftesttask.domain.model.Gif
import com.example.tinkofftesttask.domain.use_case.GetLatestGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LatestGifsViewModel @Inject constructor(
    private val getLatestGifsUseCase: GetLatestGifsUseCase
) :
    ViewModel() {

    private val _gifs = MutableStateFlow<PagingData<Gif>>(PagingData.empty())
    val gifs = _gifs.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getLatestGifsUseCase().collectLatest {
                _gifs.value = it
            }
        }
    }
}