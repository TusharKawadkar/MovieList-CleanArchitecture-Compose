package com.example.samplemovielistcleanarchitecture.core.di

import androidx.room.Room
import com.example.samplemovielistcleanarchitecture.core.MainApplication
import com.example.samplemovielistcleanarchitecture.core.database.AppDatabase
import com.example.samplemovielistcleanarchitecture.core.utils.AppConstants
import com.example.samplemovielistcleanarchitecture.core.utils.NetworkUtils
import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.local.MoviesDao
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object BaseAppModule {

    @Singleton
    @Provides
    fun provideAppReference(application: MainApplication): WeakReference<MainApplication> {
        return WeakReference(application)
    }

    @Singleton
    @Provides
    fun provideNetworkUtil(weakApp: WeakReference<MainApplication>): NetworkUtils {
        return NetworkUtils(weakApp)
    }

    @Provides
    fun provideDatabase(weakApp: WeakReference<MainApplication>): AppDatabase {
        return Room.databaseBuilder(weakApp.get()!!, AppDatabase::class.java, "app_db_2").build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(appDatabase: AppDatabase): MoviesDao {
        return appDatabase.moviesDao
    }

    @Provides
    fun provideGson(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().create())
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
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

    @Singleton
    @Provides
    fun provideRetrofit(gson: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(gson).build()
    }

}