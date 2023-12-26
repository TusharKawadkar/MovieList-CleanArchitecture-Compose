package com.example.samplemovielistcleanarchitecture.data.repository

import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseResult
import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseFailedType
import com.example.samplemovielistcleanarchitecture.core.utils.AppConstants
import com.example.samplemovielistcleanarchitecture.core.utils.NetworkUtils
import com.example.samplemovielistcleanarchitecture.data.remote.MovieListApiResponseDto
import com.example.samplemovielistcleanarchitecture.data.remote.MovieListItemDto
import com.example.samplemovielistcleanarchitecture.core.network.MoviesApiService
import retrofit2.Response
import java.lang.Exception

class MovieListRemoteRepository(
    private val api: MoviesApiService,
    private val networkUtils: NetworkUtils
) {

    fun getMoviesList(): RemoteResponseResult<List<MovieListItemDto>> {
        return if (networkUtils.isNetworkAvailable()) {
            val apiResponse = api.fetchMoviesList().execute()
            parseMovieListResponse(apiResponse)
        } else {
            RemoteResponseResult.Failed(
                RemoteResponseFailedType.NO_INTERNET,
                AppConstants.PLEASE_CHECK_CONNECTION
            )
        }
    }

    private fun parseMovieListResponse(response: Response<MovieListApiResponseDto>): RemoteResponseResult<List<MovieListItemDto>> {
        if (response.isSuccessful) {
            try {
                if (response.body() == null) {
                    return RemoteResponseResult.Failed(
                        RemoteResponseFailedType.DATA_ERROR,
                        AppConstants.SOMETHING_WENT_WRONG
                    )
                }
                return RemoteResponseResult.Success(response.body()!!.results)
            } catch (e: Exception) {
                e.printStackTrace()
                return RemoteResponseResult.Failed(
                    RemoteResponseFailedType.DATA_ERROR,
                    AppConstants.SOMETHING_WENT_WRONG
                )
            }
        } else {
            return RemoteResponseResult.Failed(
                RemoteResponseFailedType.SERVER_ERROR,
                AppConstants.SOMETHING_WENT_WRONG
            )
        }
    }

}