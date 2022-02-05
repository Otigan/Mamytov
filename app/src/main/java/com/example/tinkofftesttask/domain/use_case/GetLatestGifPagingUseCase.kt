package com.example.tinkofftesttask.domain.use_case

import com.example.tinkofftesttask.domain.repository.LatestGifsRepository
import javax.inject.Inject

class GetLatestGifPagingUseCase @Inject constructor(private val gifsRepository: LatestGifsRepository) {

    suspend operator fun invoke() = gifsRepository.getLatestGifsPaging()
}