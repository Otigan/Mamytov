package com.example.tinkofftesttask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tinkofftesttask.data.api.DevsLifeApi
import com.example.tinkofftesttask.data.datasource.HotGifsPagingSource
import com.example.tinkofftesttask.domain.model.Gif
import com.example.tinkofftesttask.domain.repository.HotGifsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HotGifsRepositoryImpl @Inject constructor(private val api: DevsLifeApi) :
    HotGifsRepository {

    override suspend fun getHotGifs(): Flow<PagingData<Gif>> =
        Pager(
            PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HotGifsPagingSource(api) }
        ).flow
}