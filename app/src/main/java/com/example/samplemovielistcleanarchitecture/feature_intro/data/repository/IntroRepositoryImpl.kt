package com.example.samplemovielistcleanarchitecture.feature_intro.data.repository

import com.example.samplemovielistcleanarchitecture.feature_intro.data.models.local.IntroSlideItem
import com.example.samplemovielistcleanarchitecture.feature_intro.data.source.local.IntroSlideLocalSource
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(private val localSource: IntroSlideLocalSource): IntroRepository{
    override fun getSlideImagesList1(): List<IntroSlideItem> {
        return localSource()
    }

    override fun getSlideImagesList2(): List<IntroSlideItem> {
        return localSource().reversed()
    }
}