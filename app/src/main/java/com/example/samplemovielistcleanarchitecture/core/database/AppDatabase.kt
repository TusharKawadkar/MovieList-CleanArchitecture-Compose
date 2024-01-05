package com.example.samplemovielistcleanarchitecture.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.local.MoviesDao

@Database(entities = [MovieItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
}