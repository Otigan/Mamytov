package com.example.tinkofftesttask.data.api

import com.example.tinkofftesttask.data.model.Result
import com.example.tinkofftesttask.domain.model.Gif

class FakeApi(val gifDtos: MutableList<Gif> = mutableListOf()) : DevsLifeApi {

    override suspend fun getLatestGifs(page: Int): Result {
        return Result(gifDtos, gifDtos.size)
    }

    override suspend fun getTopGifs(page: Int): Result {
        return Result(gifDtos, gifDtos.size)
    }

    override suspend fun getHotGifs(page: Int): Result {
        return Result(gifDtos, gifDtos.size)
    }
}