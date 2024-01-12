package com.example.samplemovielistcleanarchitecture.feature_intro.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.samplemovielistcleanarchitecture.feature_intro.data.models.local.IntroSlideItem
import com.example.samplemovielistcleanarchitecture.feature_intro.domain.usecases.SlideImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroScreenViewModel @Inject constructor(private val slideImagesUseCase:SlideImagesUseCase): ViewModel() {

    private val _slideImages1 = mutableStateOf(emptyList<IntroSlideItem>())
    val slideImagesState1: State<List<IntroSlideItem>>
        get() = _slideImages1

    private val _slideImages2 = mutableStateOf(emptyList<IntroSlideItem>())
    val slideImagesState2 = _slideImages2

    init {
        _slideImages1.value = slideImagesUseCase.imageList1()
        _slideImages2.value = slideImagesUseCase.imageList2()
    }

}