package com.example.samplemovielistcleanarchitecture.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movieitementity")
data class MovieItemEntity(
    @PrimaryKey val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val vote_count: Int,
    val release_date: String,
    val poster_path: String,
    val is_favourite: Boolean = false
)