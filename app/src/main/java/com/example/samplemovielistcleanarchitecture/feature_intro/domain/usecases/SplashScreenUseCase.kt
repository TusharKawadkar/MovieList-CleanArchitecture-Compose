package com.example.samplemovielistcleanarchitecture.feature_intro.domain.usecases

import com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.spashscreen.SplashScreenRepositoryImpl

class SplashScreenUseCase(private val repository: SplashScreenRepositoryImpl = SplashScreenRepositoryImpl()) {

    fun getImageResList(): List<Int> {
        return repository.getSplashScreenImagesRes()
    }

    fun getImageResListReversed(): List<Int> {
        return repository.getSplashScreenImagesRes().reversed()
    }
}