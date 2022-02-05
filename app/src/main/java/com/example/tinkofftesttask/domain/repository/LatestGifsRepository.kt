package com.example.tinkofftesttask.domain.repository

import androidx.paging.PagingData
import com.example.tinkofftesttask.data.model.GifDto
import com.example.tinkofftesttask.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface LatestGifsRepository {

    suspend fun getLatestGifsPaging(): Flow<PagingData<GifDto>>
}