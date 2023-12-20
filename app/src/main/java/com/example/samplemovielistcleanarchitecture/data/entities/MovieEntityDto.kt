package com.example.samplemovielistcleanarchitecture.data.entities

data class MovieEntityDto(
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val vote_count: Int?,
    val release_date: String?,
    val poster_path: String?,
    val isFavourite: Boolean,
    val ratings: Short?
)
