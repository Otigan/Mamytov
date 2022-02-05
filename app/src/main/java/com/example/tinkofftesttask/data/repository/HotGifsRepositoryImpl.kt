package com.example.tinkofftesttask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tinkofftesttask.data.datasource.HotGifsPagingSource
import com.example.tinkofftesttask.data.model.GifDto
import com.example.tinkofftesttask.domain.repository.HotGifsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HotGifsRepositoryImpl @Inject constructor(private val hotGifsPagingSource: HotGifsPagingSource) :
    HotGifsRepository {

    override suspend fun getHotGifs(): Flow<PagingData<GifDto>> =
        Pager(
            PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { hotGifsPagingSource }
        ).flow
}