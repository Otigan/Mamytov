package com.example.tinkofftesttask.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.tinkofftesttask.data.api.DevsLifeApi
import com.example.tinkofftesttask.data.model.GifDto
import com.example.tinkofftesttask.data.model.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
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
class LatestGifsPagingSourceTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    val api: DevsLifeApi = mock()

    private lateinit var latestGifPagingSource: LatestGifPagingSource

    @Before
    fun setup() {
        latestGifPagingSource = LatestGifPagingSource(api)
    }

    companion object {
        val reviewsResponse = Result(
            result = listOf(
                GifDto(
                    id = 17090,
                    description = "Unhandled exception",
                    votes = 5,
                    author = "xakep",
                    date = "Oct 26, 2021 10:05:22 AM",
                    gifURL = "http://static.devli.ru/public/images/gifs/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.gif",
                    gifSize = 8031076,
                    previewURL = "https://static.devli.ru/public/images/previews/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.jpg",
                    videoURL = "http://static.devli.ru/public/images/v/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.mp4",
                    videoPath = "/public/images/v/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.mp4",
                    videoSize = 970357,
                    type = "gif",
                    width = "480",
                    height = "592",
                    commentsCount = 1,
                    fileSize = 8031076,
                    canVote = false
                )
            ),
            totalCount = 10,
        )
        val nextReviewsResponse = Result(
            result = listOf(
                GifDto(
                    id = 17090,
                    description = "Unhandled exception",
                    votes = 5,
                    author = "xakep",
                    date = "Oct 26, 2021 10:05:22 AM",
                    gifURL = "http://static.devli.ru/public/images/gifs/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.gif",
                    gifSize = 8031076,
                    previewURL = "https://static.devli.ru/public/images/previews/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.jpg",
                    videoURL = "http://static.devli.ru/public/images/v/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.mp4",
                    videoPath = "/public/images/v/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.mp4",
                    videoSize = 970357,
                    type = "gif",
                    width = "480",
                    height = "592",
                    commentsCount = 1,
                    fileSize = 8031076,
                    canVote = false
                )
            ),
            totalCount = 10,
        )
    }


    @Test
    fun `LOAD RETURNS 404 HTTP ERROR`() = runTest {
        val error = HttpException(
            Response.error<Any>(
                404, ResponseBody.create(
                    MediaType.parse("plain/text"), ""
                )
            )
        )
        given(api.getLatestGifs(any())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, GifDto>(error)
        assertEquals(
            expectedResult, latestGifPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `gif paging source load - failure - received null`() = runTest {
        given(api.getLatestGifs(any())).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, GifDto>(NullPointerException())
        assertEquals(
            expectedResult.toString(), latestGifPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

    @Test
    fun `gifs paging source refresh - success`() = runTest {
        given(api.getLatestGifs(any())).willReturn(reviewsResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = reviewsResponse.result,
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, latestGifPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
}