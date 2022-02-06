package com.example.tinkofftesttask.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.tinkofftesttask.data.api.DevsLifeApi
import com.example.tinkofftesttask.domain.model.Gif
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class TopGifsPagingSourceTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    val api: DevsLifeApi = mock()

    private lateinit var topGifsPagingSource: TopGifsPagingSource

    @Before
    fun setup() {
        topGifsPagingSource = TopGifsPagingSource(api)
    }


    @Test
    fun `gifs paging source refresh - fail 404 HTTP`() = runTest {
        val error = HttpException(
            Response.error<Any>(
                404, ResponseBody.create(
                    MediaType.parse("plain/text"), ""
                )
            )
        )
        given(api.getTopGifs(any())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, Gif>(error)
        val realResult = topGifsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assertThat(expectedResult).isEqualTo(realResult)
    }

    @Test
    fun `gif paging source load - failure - received null`() = runTest {
        given(api.getTopGifs(any())).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, Gif>(NullPointerException())
        val realResult = topGifsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assertThat(expectedResult.toString()).isEqualTo(realResult.toString())
    }
}