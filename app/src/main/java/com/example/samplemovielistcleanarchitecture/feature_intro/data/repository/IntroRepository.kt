package com.example.samplemovielistcleanarchitecture.feature_intro.data.repository

import com.example.samplemovielistcleanarchitecture.feature_intro.data.models.local.IntroSlideItem

interface IntroRepository {
    fun getSlideImagesList1(): List<IntroSlideItem>
    fun getSlideImagesList2(): List<IntroSlideItem>
}