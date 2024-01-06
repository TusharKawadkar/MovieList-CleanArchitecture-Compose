package com.example.samplemovielistcleanarchitecture.core.di

import com.example.samplemovielistcleanarchitecture.feature_intro.data.repository.IntroRepositoryImpl
import com.example.samplemovielistcleanarchitecture.feature_intro.data.repository.IntroRepository
import com.example.samplemovielistcleanarchitecture.feature_intro.data.source.local.IntroSlideLocalSource
import com.example.samplemovielistcleanarchitecture.feature_intro.domain.usecases.GetSlideImageList1
import com.example.samplemovielistcleanarchitecture.feature_intro.domain.usecases.GetSlideImageList2
import com.example.samplemovielistcleanarchitecture.feature_intro.domain.usecases.SlideImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object IntroDependenciesModule {

    @Provides
    fun provideIntroLocalSource(): IntroSlideLocalSource {
        return IntroSlideLocalSource()
    }

    @Provides
    fun provideIntroRepository(localSource: IntroSlideLocalSource): IntroRepository {
        return IntroRepositoryImpl(localSource)
    }

    @Provides
    fun provideIntroUseCase(repository: IntroRepository): SlideImagesUseCase {
        return SlideImagesUseCase(
            imageList1 = GetSlideImageList1(repository),
            imageList2 = GetSlideImageList2(repository)
        )
    }

}