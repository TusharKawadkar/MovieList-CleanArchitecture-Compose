package com.example.samplemovielistcleanarchitecture.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samplemovielistcleanarchitecture.data.local.entities.MovieItemEntity

@Database(entities = [MovieItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
}