package com.example.samplemovielistcleanarchitecture.feature_intro.domain.usecases

import com.example.samplemovielistcleanarchitecture.feature_intro.data.models.local.IntroSlideItem
import com.example.samplemovielistcleanarchitecture.feature_intro.data.repository.IntroRepository
import javax.inject.Inject

class GetSlideImageList1 @Inject constructor(private val repository: IntroRepository) {
    operator fun invoke(): List<IntroSlideItem> {
        return repository.getSlideImagesList1()
    }
}