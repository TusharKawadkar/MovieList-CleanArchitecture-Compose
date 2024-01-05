package com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist

import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseResult
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.data.models.local.remote.MovieListApiResponseDto
import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.remote.MovieListRemote
import kotlinx.coroutines.flow.Flow

class MovieListRepositoryImpl(
    private val localRepository: MovieListLocalRepository,
    private val remoteRepository: MovieListRemote
) {

    fun observeMovieList(): Flow<List<MovieItemEntity>> {
        return localRepository.observeMovieList()
    }

    suspend fun callMovieListApi(): Flow<RemoteResponseResult<MovieListApiResponseDto>> {
        return remoteRepository.getMoviesList()
    }

    fun updateRemoteDataToDb(movieList: List<MovieListItemDto>) {
        val dataList = movieList.map {
            MovieItemEntity(
                it.id,
                it.original_language,
                it.original_title,
                it.overview,
                it.vote_count,
                it.release_date,
                it.poster_path
            )
        }
        localRepository.addToDatabase(dataList)
    }

}