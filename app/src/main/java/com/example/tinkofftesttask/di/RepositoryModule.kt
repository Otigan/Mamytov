package com.example.tinkofftesttask.di

import com.example.tinkofftesttask.data.repository.HotGifsRepositoryImpl
import com.example.tinkofftesttask.data.repository.LatestGifsRepositoryImpl
import com.example.tinkofftesttask.data.repository.TopGifsRepositoryImpl
import com.example.tinkofftesttask.domain.repository.HotGifsRepository
import com.example.tinkofftesttask.domain.repository.LatestGifsRepository
import com.example.tinkofftesttask.domain.repository.TopGifsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLatestGifRepository(impl: LatestGifsRepositoryImpl): LatestGifsRepository

    @Binds
    @Singleton
    abstract fun bindTopGifRepository(impl: TopGifsRepositoryImpl): TopGifsRepository

    @Binds
    @Singleton
    abstract fun bindHotGifRepository(impl: HotGifsRepositoryImpl): HotGifsRepository

}