package com.example.tinkofftesttask.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tinkofftesttask.data.api.DevsLifeApi
import com.example.tinkofftesttask.data.model.GifDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LatestGifPagingSource @Inject constructor(private val api: DevsLifeApi) :
    PagingSource<Int, GifDto>() {

    override fun getRefreshKey(state: PagingState<Int, GifDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifDto> {
        val position = params.key ?: 1
        return try {
            val response = api.getLatestGifs(position)

            LoadResult.Page(
                response.result,
                prevKey = if (position == 1) null else position.minus(1),
                nextKey = if (response.result.isEmpty()) null else position.plus(1)
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: NullPointerException) {
            LoadResult.Error(e)
        }
    }
}