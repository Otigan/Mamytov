package com.example.tinkofftesttask.data.api

import com.example.tinkofftesttask.data.model.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DevsLifeApi {

    companion object {
        const val BASE_URL = "https://developerslife.ru/"
    }

    @GET("latest/{page}")
    suspend fun getLatestGifs(
        @Path(value = "page", encoded = true) page: Int,
        @Query("json") json: Boolean = true
    ): Result

    @GET("top/{page}")
    suspend fun getTopGifs(
        @Path(value = "page", encoded = true) page: Int,
        @Query("json") json: Boolean
    )

    @GET("hot/{page}")
    suspend fun getHotGifs(
        @Path(value = "page", encoded = true) page: Int,
        @Query("json") json: Boolean
    )


}