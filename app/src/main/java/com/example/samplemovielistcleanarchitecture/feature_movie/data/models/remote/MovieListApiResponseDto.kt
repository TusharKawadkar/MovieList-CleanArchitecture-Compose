package com.example.samplemovielistcleanarchitecture.feature_movie.data.models.remote

data class MovieListApiResponseDto(
    val page: Int? = null,
    val results: List<MovieListItemDto>? = null
)

data class MovieListItemDto(
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val vote_count: Int,
    val release_date: String,
    val poster_path: String
)