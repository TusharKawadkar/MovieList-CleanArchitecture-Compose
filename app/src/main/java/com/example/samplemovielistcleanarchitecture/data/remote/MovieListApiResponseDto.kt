package com.example.samplemovielistcleanarchitecture.data.remote

data class MovieListApiResponseDto(val page: Int, val results: List<MovieListItemDto>)
