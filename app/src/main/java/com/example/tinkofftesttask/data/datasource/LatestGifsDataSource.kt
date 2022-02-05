package com.example.tinkofftesttask.data.datasource

import com.example.tinkofftesttask.data.api.DevsLifeApi
import com.example.tinkofftesttask.data.model.Result
import javax.inject.Inject

class LatestGifsDataSource @Inject constructor(private val api: DevsLifeApi) {

    suspend fun getLatestGifs(page: Int): Result = api.getLatestGifs(page)

}