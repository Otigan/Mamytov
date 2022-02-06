package com.example.tinkofftesttask.domain.repository

import androidx.paging.PagingData
import com.example.tinkofftesttask.domain.model.Gif
import kotlinx.coroutines.flow.Flow

interface LatestGifsRepository {

    suspend fun getLatestGifs(): Flow<PagingData<Gif>>
}