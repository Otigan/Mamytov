package com.example.tinkofftesttask.domain.model

data class Gif(
    val date: String,
    val description: String,
    val fileSize: Int,
    val gifSize: Int,
    val gifURL: String,
    val height: String,
    val id: Int,
    val previewURL: String,
    val type: String,
    val videoPath: String,
    val videoSize: Int,
    val videoURL: String,
    val votes: Int,
    val width: String
)