package com.example.tinkofftesttask.domain.use_case

import com.example.tinkofftesttask.domain.repository.TopGifsRepository
import javax.inject.Inject

class GetTopGifsUseCase @Inject constructor(private val topGifsRepository: TopGifsRepository) {

    suspend operator fun invoke() = topGifsRepository.getTopGifs()
}