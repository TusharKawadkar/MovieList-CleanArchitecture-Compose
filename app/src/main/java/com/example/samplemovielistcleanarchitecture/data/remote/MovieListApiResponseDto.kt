package com.example.samplemovielistcleanarchitecture.data.remote

data class MovieListApiResponseDto(
    val page: Int? = null,
    val results: List<MovieListItemDto>? = null
)