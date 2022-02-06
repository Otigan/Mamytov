package com.example.tinkofftesttask.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState

class FakeLatestGifDataSource() : PagingSource<Int, GifDto>() {
    var triggerError = false
    var gifDtos: List<GifDto> = emptyList()
        set(value) {
            println("set")
            field = value
            invalidate()
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifDto> {
        println("load")
        if (triggerError) {
            return LoadResult.Error(Exception("A test error triggered"))
        }
        println("not error")

        return LoadResult.Page(
            data = gifDtos,
            prevKey = null,
            nextKey = null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, GifDto>): Int {
        println("refresh")
        return state.anchorPosition ?: 1
    }
}