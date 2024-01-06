package com.example.samplemovielistcleanarchitecture.core.di

import com.example.samplemovielistcleanarchitecture.core.utils.NetworkUtils
import com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist.MovieListRepository
import com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist.MovieListRepositoryImpl
import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.local.MoviesDao
import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.remote.MoviesApiService
import com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases.GetMovieList
import com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesDependenciesModule {

    @Provides
    fun provideApiService(retrofit: Retrofit): MoviesApiService {
        return retrofit.create(MoviesApiService::class.java)
    }

    @Provides
    fun provideMovieListRepository(
        dao: MoviesDao,
        apiService: MoviesApiService,
        networkUtils: NetworkUtils
    ): MovieListRepository {
        return MovieListRepositoryImpl(dao, apiService, networkUtils)
    }

    @Singleton
    @Provides
    fun provideMovieUseCase(repository: MovieListRepository): MovieUseCase {
        return MovieUseCase(getMovie = GetMovieList(repository))
    }
}