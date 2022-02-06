package com.example.tinkofftesttask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tinkofftesttask.data.datasource.TopGifsPagingSource
import com.example.tinkofftesttask.domain.model.Gif
import com.example.tinkofftesttask.domain.repository.TopGifsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopGifsRepositoryImpl @Inject constructor(private val topGifsPagingSource: TopGifsPagingSource) :
    TopGifsRepository {

    override suspend fun getTopGifs(): Flow<PagingData<Gif>> =
        Pager(
            PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { topGifsPagingSource }
        ).flow
}