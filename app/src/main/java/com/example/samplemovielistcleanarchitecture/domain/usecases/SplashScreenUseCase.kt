package com.example.samplemovielistcleanarchitecture.domain.usecases

import com.example.samplemovielistcleanarchitecture.data.repository.spashscreen.SplashScreenRepository

class SplashScreenUseCase(private val repository: SplashScreenRepository = SplashScreenRepository()) {

    fun getImageResList(): List<Int> {
        return repository.getSplashScreenImagesRes()
    }

    fun getImageResListReversed(): List<Int> {
        return repository.getSplashScreenImagesRes().reversed()
    }
}