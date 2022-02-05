package com.example.tinkofftesttask.domain.use_case

import com.example.tinkofftesttask.domain.repository.HotGifsRepository
import javax.inject.Inject

class GetHotGifsUseCase @Inject constructor(private val hotGifsRepository: HotGifsRepository) {

    suspend operator fun invoke() = hotGifsRepository.getHotGifs()
}