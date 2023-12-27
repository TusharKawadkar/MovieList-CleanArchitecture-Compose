package com.example.samplemovielistcleanarchitecture.core.network

import com.example.samplemovielistcleanarchitecture.data.remote.MovieListApiResponseDto
import retrofit2.Call
import retrofit2.http.GET

interface MoviesApiService {

    @GET("movie/popular")
    suspend fun fetchMoviesList(): MovieListApiResponseDto

}