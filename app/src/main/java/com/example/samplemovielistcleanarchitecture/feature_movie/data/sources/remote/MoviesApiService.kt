package com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.remote

import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.remote.MovieListApiResponseDto
import retrofit2.http.GET

interface MoviesApiService {
    @GET("movie/popular")
    suspend fun fetchMoviesList(): MovieListApiResponseDto
}