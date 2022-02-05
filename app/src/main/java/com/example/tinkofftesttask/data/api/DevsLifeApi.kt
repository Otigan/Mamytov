package com.example.tinkofftesttask.data.api

import com.example.tinkofftesttask.data.model.Result
import retrofit2.http.GET
import retrofit2.http.Path


interface DevsLifeApi {

    companion object {
        const val BASE_URL = "https://developerslife.ru/"
    }

    @GET("latest/{page}?json=true")
    suspend fun getLatestGifs(
        @Path(value = "page", encoded = true) page: Int,
    ): Result

    @GET("top/{page}?json=true")
    suspend fun getTopGifs(
        @Path(value = "page", encoded = true) page: Int,
    ): Result

    @GET("hot/{page}?json=true")
    suspend fun getHotGifs(
        @Path(value = "page", encoded = true) page: Int,
    ): Result


}