package com.example.tinkofftesttask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkofftesttask.data.model.GifDto
import com.example.tinkofftesttask.data.util.Resource
import com.example.tinkofftesttask.domain.use_case.GetLatestGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class GifsEvent() {
    data class Success(val gifs: List<GifDto>) : GifsEvent()
    data class Error(val errorMessage: String) : GifsEvent()
    object Loading : GifsEvent()
}

@HiltViewModel
class LatestGifsViewModel @Inject constructor(private val getLatestGifsUseCase: GetLatestGifsUseCase) :
    ViewModel() {

    private val gifsEventChannel = Channel<GifsEvent>(Channel.BUFFERED)
    val gifsFlow = gifsEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            gifsEventChannel.send(GifsEvent.Loading)
            getLatestGifsUseCase(1).collect { resource ->
                when (resource) {
                    is Resource.Error -> resource.errorMessage?.let { errorMessage ->
                        gifsEventChannel.send(GifsEvent.Error(errorMessage))
                    }
                    is Resource.Success -> resource.data?.let { gifs ->
                        GifsEvent.Success(gifs)
                    }?.let { gifsEventChannel.send(it) }
                }
            }
        }
    }

}