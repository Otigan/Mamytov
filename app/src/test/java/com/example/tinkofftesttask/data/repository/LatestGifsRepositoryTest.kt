package com.example.tinkofftesttask.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DifferCallback
import androidx.paging.NullPaddedList
import androidx.paging.PagingData
import androidx.paging.PagingDataDiffer
import com.example.tinkofftesttask.data.api.FakeApi
import com.example.tinkofftesttask.domain.repository.LatestGifsRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LatestGifsRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeApi: FakeApi
    private lateinit var latestGifsRepository: LatestGifsRepository
    private lateinit var dummyResponse: MutableList<GifDto>
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(coroutineDispatcher)
        dummyResponse = mutableListOf(
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
            ),
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
        )
        fakeApi = FakeApi(dummyResponse)
        latestGifsRepository = LatestGifsRepositoryImpl(fakeApi)
    }


    @Test
    fun `load gifs successfully`() = runTest {
        val tmp = latestGifsRepository.getLatestGifsPaging().take(1).toList().first()
        val result = tmp.collectDataForTest()
        assertThat(dummyResponse).isEqualTo(result)
    }

    private suspend fun <T : Any> PagingData<T>.collectDataForTest(): List<T> {
        val dcb = object : DifferCallback {
            override fun onChanged(position: Int, count: Int) {}
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
        }
        val items = mutableListOf<T>()
        val dif = object : PagingDataDiffer<T>(dcb) {
            override suspend fun presentNewList(
                previousList: NullPaddedList<T>,
                newList: NullPaddedList<T>,
                lastAccessedIndex: Int,
                onListPresentable: () -> Unit
            ): Int? {
                for (idx in 0 until newList.size)
                    items.add(newList.getFromStorage(idx))
                onListPresentable()
                return null
            }
        }
        dif.collectFrom(this)
        return items
    }
}