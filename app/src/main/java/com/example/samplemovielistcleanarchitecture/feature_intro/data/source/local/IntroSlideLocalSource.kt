package com.example.samplemovielistcleanarchitecture.feature_intro.data.source.local

import com.example.samplemovielistcleanarchitecture.R
import com.example.samplemovielistcleanarchitecture.feature_intro.data.models.local.IntroSlideItem

class IntroSlideLocalSource {
    operator fun invoke(): List<IntroSlideItem> {
        val mutableList = mutableListOf<IntroSlideItem>()
        mutableList.add(IntroSlideItem(R.drawable.movie1))
        mutableList.add(IntroSlideItem(R.drawable.movie2))
        mutableList.add(IntroSlideItem(R.drawable.movie3))
        mutableList.add(IntroSlideItem(R.drawable.movie4))
        mutableList.add(IntroSlideItem(R.drawable.movie5))
        mutableList.add(IntroSlideItem(R.drawable.movie6))
        mutableList.add(IntroSlideItem(R.drawable.movie7))
        mutableList.add(IntroSlideItem(R.drawable.movie8))
        return mutableList
    }
}