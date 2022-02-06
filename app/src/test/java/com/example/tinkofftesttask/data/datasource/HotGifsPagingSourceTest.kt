package com.example.tinkofftesttask.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.tinkofftesttask.data.api.DevsLifeApi
import com.example.tinkofftesttask.data.model.Result
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
class HotGifsPagingSourceTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    val api: DevsLifeApi = mock()

    private lateinit var hotGifsPagingSource: HotGifsPagingSource

    @Before
    fun setup() {
        hotGifsPagingSource = HotGifsPagingSource(api)
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
        given(api.getHotGifs(any())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, Gif>(error)
        val realResult = hotGifsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assertThat(expectedResult).isEqualTo(realResult)
    }

    @Test
    fun `hot gif paging source load - failure - received null`() = runTest {
        given(api.getHotGifs(any())).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, Gif>(NullPointerException())
        val realResult = hotGifsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assertThat(expectedResult.toString()).isEqualTo(realResult.toString())
    }
}