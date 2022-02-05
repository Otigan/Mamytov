package com.example.tinkofftesttask.presentation

import androidx.lifecycle.ViewModel
import com.example.tinkofftesttask.domain.use_case.CheckConnectivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(private val checkConnectivityUseCase: CheckConnectivityUseCase) :
    ViewModel() {

    private val _hasInternet = MutableStateFlow<Boolean>(true)
    val hasInternet = _hasInternet.asStateFlow()


    init {
        _hasInternet.value = checkConnectivityUseCase()
    }
}