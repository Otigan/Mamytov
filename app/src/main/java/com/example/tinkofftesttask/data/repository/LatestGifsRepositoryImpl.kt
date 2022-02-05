package com.example.tinkofftesttask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tinkofftesttask.data.datasource.LatestGifPagingSource
import com.example.tinkofftesttask.data.datasource.LatestGifsDataSource
import com.example.tinkofftesttask.data.model.GifDto
import com.example.tinkofftesttask.data.util.Resource
import com.example.tinkofftesttask.data.util.ResponseHandler
import com.example.tinkofftesttask.domain.repository.LatestGifsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LatestGifsRepositoryImpl @Inject constructor(
    private val latestGifsDataSource: LatestGifsDataSource,
    private val responseHandler: ResponseHandler,
    private val latestGifPagingSource: LatestGifPagingSource
) :
    LatestGifsRepository {

    override suspend fun getLatestGifs(page: Int): Flow<Resource<List<GifDto>>> {
        return try {
            val response = latestGifsDataSource.getLatestGifs(page)
            return flow {
                emit(responseHandler.handleSuccess(response.result))
            }
        } catch (e: Exception) {
            flow {
                emit(responseHandler.handleError(e, null))
            }
        }
    }

    override suspend fun getLatestGifsPaging(): Flow<PagingData<GifDto>> =
        Pager(
            PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { latestGifPagingSource }
        ).flow

}