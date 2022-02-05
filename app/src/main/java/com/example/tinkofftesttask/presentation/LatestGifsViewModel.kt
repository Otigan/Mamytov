package com.example.tinkofftesttask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.tinkofftesttask.data.model.GifDto
import com.example.tinkofftesttask.domain.use_case.GetLatestGifPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class GifsEvent() {
    data class Success(val gifs: List<GifDto>) : GifsEvent()
    data class Error(val errorMessage: String) : GifsEvent()
    object Loading : GifsEvent()
}

@HiltViewModel
class LatestGifsViewModel @Inject constructor(
    private val getLatestGifPagingUseCase: GetLatestGifPagingUseCase
) :
    ViewModel() {

    private val _gifs = MutableStateFlow<PagingData<GifDto>>(PagingData.empty())
    val gifs = _gifs.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getLatestGifPagingUseCase().collectLatest {
                _gifs.value = it
            }
        }
    }
}