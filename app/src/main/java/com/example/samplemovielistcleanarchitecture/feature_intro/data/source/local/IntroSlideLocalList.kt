package com.example.samplemovielistcleanarchitecture.feature_intro.data.source.local

import com.example.samplemovielistcleanarchitecture.R

class IntroSlideLocalList {
    fun getSplashScreenImagesRes(): List<Int> {
        val mutableList = mutableListOf<Int>()
        mutableList.add(R.drawable.movie1)
        mutableList.add(R.drawable.movie2)
        mutableList.add(R.drawable.movie3)
        mutableList.add(R.drawable.movie4)
        mutableList.add(R.drawable.movie5)
        mutableList.add(R.drawable.movie6)
        mutableList.add(R.drawable.movie7)
        mutableList.add(R.drawable.movie8)
        return mutableList
    }
}