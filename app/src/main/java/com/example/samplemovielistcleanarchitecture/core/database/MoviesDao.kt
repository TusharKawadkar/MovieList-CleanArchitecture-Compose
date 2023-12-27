package com.example.samplemovielistcleanarchitecture.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.samplemovielistcleanarchitecture.data.local.entities.MovieItemEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Query("SELECT * from movieitementity")
    fun observeMovieList(): Flow<List<MovieItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMoviesToDb(moviesList: MovieItemEntity)

}