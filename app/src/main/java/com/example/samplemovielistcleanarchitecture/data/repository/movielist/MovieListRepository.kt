package com.example.samplemovielistcleanarchitecture.data.repository.movielist

import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseResult
import com.example.samplemovielistcleanarchitecture.data.local.entities.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.data.remote.MovieListItemDto
import kotlinx.coroutines.flow.Flow

class MovieListRepository(
    private val localRepository: MovieListLocalRepository,
    private val remoteRepository: MovieListRemoteRepository
) {

    fun observeMovieList(): Flow<List<MovieItemEntity>> {
        return localRepository.observeMovieList()
    }

    fun callMovieListApi(): RemoteResponseResult<List<MovieListItemDto>> {
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