package com.example.samplemovielistcleanarchitecture.data.repository

import com.example.samplemovielistcleanarchitecture.core.database.MoviesDao
import com.example.samplemovielistcleanarchitecture.data.local.entities.MovieItemEntity
import kotlinx.coroutines.flow.Flow

class MovieListLocalRepository(private val dao: MoviesDao) {

    fun addToDatabase(moviesList: List<MovieItemEntity>) {
        dao.addMoviesToDb(moviesList)
    }

    fun observeMovieList(): Flow<List<MovieItemEntity>> {
        return dao.observeMovieList()
    }

}