package com.example.tinkofftesttask.data.repository

import com.example.tinkofftesttask.data.datasource.LatestGifsDataSource
import com.example.tinkofftesttask.data.model.GifDto
import com.example.tinkofftesttask.data.util.Resource
import com.example.tinkofftesttask.data.util.ResponseHandler
import com.example.tinkofftesttask.domain.repository.LatestGifsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LatestGifsRepositoryImpl @Inject constructor(
    private val latestGifsDataSource: LatestGifsDataSource,
    private val responseHandler: ResponseHandler
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
}