package com.example.tinkofftesttask.di

import com.example.tinkofftesttask.data.repository.LatestGifsRepositoryImpl
import com.example.tinkofftesttask.domain.repository.LatestGifsRepository
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

}