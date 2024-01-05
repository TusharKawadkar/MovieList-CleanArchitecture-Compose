package com.example.samplemovielistcleanarchitecture.core.repository

import android.app.Application
import androidx.room.Room
import com.example.samplemovielistcleanarchitecture.core.MainApplication
import com.example.samplemovielistcleanarchitecture.core.database.AppDatabase
import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.local.MoviesDao
import com.example.samplemovielistcleanarchitecture.core.network.MoviesApiService
import com.example.samplemovielistcleanarchitecture.core.utils.AppConstants
import com.example.samplemovielistcleanarchitecture.core.utils.NetworkUtils
import com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist.MovieListLocalRepository
import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.remote.MovieListRemote
import com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist.MovieListRepositoryImpl
import com.example.samplemovielistcleanarchitecture.domain.usecases.MovieListUseCase
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference


class AppRepository {

    private constructor() {}

    companion object {
        private var INSTANCE: AppRepository? = null

        @Synchronized
        fun initInstance(application: MainApplication) {
            if (INSTANCE == null) {
                INSTANCE = AppRepository().also {
                    it.initRepositories(application)
                }
            }
        }

        @Synchronized
        fun getInstance(): AppRepository {
            return INSTANCE!!
        }
    }

    private lateinit var weakApp: WeakReference<Application>
    private lateinit var moviesDao: MoviesDao
    private lateinit var networkUtils: NetworkUtils
    private lateinit var moviesApiService: MoviesApiService
    var movieListUseCase: MovieListUseCase? = null

    private fun initRepositories(application: Application) {
        weakApp = WeakReference<Application>(application)
        networkUtils = NetworkUtils(weakApp)
        moviesDao = createMoviesDao()
        moviesApiService = createRetrofit().create(MoviesApiService::class.java)

        //init use cases
        movieListUseCase = createMovieListUseCase()
    }

    private fun createMovieListUseCase(): MovieListUseCase {
        val localRepo = MovieListLocalRepository(moviesDao)
        val remoteRepo = MovieListRemote(moviesApiService, networkUtils)
        val movieListRepo = MovieListRepositoryImpl(localRepo, remoteRepo)
        return MovieListUseCase(movieListRepo)
    }

    private fun createMoviesDao(): MoviesDao {
        return createMoviesDatabase().moviesDao
    }

    private fun createMoviesDatabase(): AppDatabase {
        return Room.databaseBuilder(weakApp.get()!!, AppDatabase::class.java, "app_db_2")
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addNetworkInterceptor(Interceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NTZlNGJmYzAzNGIzMDUyMjM5MGE0Zjg4MWMxZjlhZiIsInN1YiI6IjY1ODFhMzM4YmYwZjYzMDg3NTYyY2U3MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-03M7NZGmLuBeBGAv5pDWOD7yEfvOexo86Y_2CkUOwM"
            )
            chain.proceed(requestBuilder.build())
        }).addInterceptor(HttpLoggingInterceptor().also { loggingInterceptor ->
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()
    }

    private fun createRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .create()

        return Retrofit.Builder()
            .client(createOkHttpClient())
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            )
            .build()
    }

}