package com.example.tinkofftesttask.domain.repository

import androidx.paging.PagingData
import com.example.tinkofftesttask.data.model.GifDto
import kotlinx.coroutines.flow.Flow

interface TopGifsRepository {

    suspend fun getTopGifs(): Flow<PagingData<GifDto>>
}