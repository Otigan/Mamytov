package com.example.tinkofftesttask.domain.use_case

import com.example.tinkofftesttask.data.datasource.InternetConnectionCheck
import javax.inject.Inject

class CheckConnectivityUseCase @Inject constructor(private val internetConnectionCheck: InternetConnectionCheck) {

    operator fun invoke() = internetConnectionCheck.hasInternetConnection()
}