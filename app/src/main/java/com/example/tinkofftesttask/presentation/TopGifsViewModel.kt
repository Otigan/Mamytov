package com.example.tinkofftesttask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tinkofftesttask.domain.model.Gif
import com.example.tinkofftesttask.domain.use_case.GetTopGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopGifsViewModel @Inject constructor(private val getTopGifsUseCase: GetTopGifsUseCase) :
    ViewModel() {

    private val _gifs = MutableStateFlow<PagingData<Gif>>(PagingData.empty())
    val gifs = _gifs.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTopGifsUseCase().cachedIn(viewModelScope).collectLatest {
                _gifs.value = it
            }
        }
    }
}