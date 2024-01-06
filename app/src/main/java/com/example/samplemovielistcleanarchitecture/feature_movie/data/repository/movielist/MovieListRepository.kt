package com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist

import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseResult
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.remote.MovieListApiResponseDto
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.remote.MovieListItemDto
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun observeMovieList(): Flow<List<MovieItemEntity>>

    suspend fun getMoviesListRemote(): Flow<RemoteResponseResult<MovieListApiResponseDto>>

    suspend fun updateRemoteDataToDb(movieList: List<MovieListItemDto>)
}