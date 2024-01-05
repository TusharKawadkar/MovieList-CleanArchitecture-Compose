package com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist

import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.local.MoviesDao
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import kotlinx.coroutines.flow.Flow

class MovieListLocalRepository(private val dao: MoviesDao) {

    fun addToDatabase(moviesList: List<MovieItemEntity>) {
        repeat(moviesList.size) {
            dao.addMoviesToDb(moviesList[it])
        }

    }

    fun observeMovieList(): Flow<List<MovieItemEntity>> {
        return dao.observeMovieList()
    }

}