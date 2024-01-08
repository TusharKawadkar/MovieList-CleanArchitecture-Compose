package com.example.samplemovielistcleanarchitecture.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.local.MoviesDao

@Database(entities = [MovieItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
    companion object{
        val DB_NAME = "app_db_5"
    }
}