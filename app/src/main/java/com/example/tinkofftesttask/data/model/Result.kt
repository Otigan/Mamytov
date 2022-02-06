package com.example.tinkofftesttask.data.model

import com.example.tinkofftesttask.domain.model.Gif

data class Result(
    val result: List<Gif>,
    val totalCount: Int
)