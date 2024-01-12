package com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist

import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity


object FakeMovieListRepository {

    fun getMovieList(): List<MovieItemEntity> {
        val mutableList = mutableListOf<MovieItemEntity>()
        repeat(10) {
            mutableList.add(testMovieItem.copy())
        }
        return mutableList
    }

    val testMovieItem = MovieItemEntity(
        1,
        "en",
        "The Hunger Games: The Ballad of Songbirds",
        "64 years before he becomes the tyrannical president of Panem, Coriolanus Snow sees a chance for a change in fortunes when he mentors Lucy Gray Baird, the female tribute from District 12.",
        200,
        "2023-11-15",
        "/5a4JdoFwll5DRtKMe7JLuGQ9yJm.jpg",
        false
    )
}