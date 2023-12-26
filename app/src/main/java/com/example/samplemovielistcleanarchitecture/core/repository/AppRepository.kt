package com.example.samplemovielistcleanarchitecture.core.repository

import android.app.Application
import androidx.room.Room
import com.example.samplemovielistcleanarchitecture.core.database.AppDatabase
import com.example.samplemovielistcleanarchitecture.core.database.MoviesDao
import com.example.samplemovielistcleanarchitecture.core.network.MoviesApiService
import com.example.samplemovielistcleanarchitecture.core.utils.AppConstants
import com.example.samplemovielistcleanarchitecture.core.utils.NetworkUtils
import com.example.samplemovielistcleanarchitecture.data.repository.movielist.MovieListLocalRepository
import com.example.samplemovielistcleanarchitecture.data.repository.movielist.MovieListRemoteRepository
import com.example.samplemovielistcleanarchitecture.data.repository.movielist.MovieListRepository
import com.example.samplemovielistcleanarchitecture.domain.usecases.MovieListUseCase
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference

object AppRepository {

    private lateinit var weakApp: WeakReference<Application>
    private lateinit var moviesDao: MoviesDao
    private lateinit var networkUtils: NetworkUtils
    private lateinit var moviesApiService: MoviesApiService
    lateinit var movieListUseCase : MovieListUseCase

    fun init(application: Application) {
        weakApp = WeakReference<Application>(application)
        networkUtils = NetworkUtils(weakApp)
        moviesDao = createMoviesDao()
        moviesApiService = createRetrofit().create(MoviesApiService::class.java)

        //init use cases
        movieListUseCase = createMovieListUseCase()
    }

    private fun createMovieListUseCase(): MovieListUseCase {
        val localRepo = MovieListLocalRepository(moviesDao)
        val remoteRepo = MovieListRemoteRepository(moviesApiService, networkUtils)
        val movieListRepo = MovieListRepository(localRepo, remoteRepo)
        return MovieListUseCase(movieListRepo)
    }

    private fun createMoviesDao(): MoviesDao {
        return createMoviesDatabase().moviesDao
    }

    private fun createMoviesDatabase(): AppDatabase {
        return Room.databaseBuilder(weakApp.get()!!, AppDatabase::class.java, "app_db")
            .addTypeConverter(Gson())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient().also {
            it.networkInterceptors().add(Interceptor { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                requestBuilder.header(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NTZlNGJmYzAzNGIzMDUyMjM5MGE0Zjg4MWMxZjlhZiIsInN1YiI6IjY1ODFhMzM4YmYwZjYzMDg3NTYyY2U3MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-03M7NZGmLuBeBGAv5pDWOD7yEfvOexo86Y_2CkUOwM"
                )
                chain.proceed(requestBuilder.build())
            })
            it.interceptors().add(HttpLoggingInterceptor().also { loggingInterceptor ->
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            })
        }
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(createOkHttpClient())
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}