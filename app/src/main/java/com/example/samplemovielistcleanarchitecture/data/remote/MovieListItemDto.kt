package com.example.samplemovielistcleanarchitecture.data.remote

data class MovieListItemDto(
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val vote_count: Int,
    val release_date: String,
    val poster_path: String
)