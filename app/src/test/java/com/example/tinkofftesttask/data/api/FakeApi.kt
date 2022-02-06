package com.example.tinkofftesttask.data.api

import com.example.tinkofftesttask.data.model.Result

class FakeApi(val gifDtos: MutableList<GifDto> = mutableListOf()) : DevsLifeApi {

    override suspend fun getLatestGifs(page: Int): Result {
        return Result(gifDtos, gifDtos.size)
    }

    override suspend fun getTopGifs(page: Int): Result {
        TODO("Not yet implemented")
    }

    override suspend fun getHotGifs(page: Int): Result {
        TODO("Not yet implemented")
    }
}