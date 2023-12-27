package com.example.samplemovielistcleanarchitecture.data.repository.movielist

import com.example.samplemovielistcleanarchitecture.core.database.MoviesDao
import com.example.samplemovielistcleanarchitecture.data.local.entities.MovieItemEntity
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