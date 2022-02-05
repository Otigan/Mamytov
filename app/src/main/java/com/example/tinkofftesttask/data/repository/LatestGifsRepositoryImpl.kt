package com.example.tinkofftesttask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tinkofftesttask.data.api.DevsLifeApi
import com.example.tinkofftesttask.data.datasource.LatestGifPagingSource
import com.example.tinkofftesttask.data.model.GifDto
import com.example.tinkofftesttask.domain.repository.LatestGifsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LatestGifsRepositoryImpl @Inject constructor(
    private val api: DevsLifeApi
) :
    LatestGifsRepository {


    override suspend fun getLatestGifsPaging(): Flow<PagingData<GifDto>> =
        Pager(
            PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { LatestGifPagingSource(api) }
        ).flow

}