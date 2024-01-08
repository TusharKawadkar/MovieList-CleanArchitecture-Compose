package com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Query("SELECT * from movieitementity")
    fun observeMovieList(): Flow<List<MovieItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMoviesToDb(moviesList: MovieItemEntity)

}