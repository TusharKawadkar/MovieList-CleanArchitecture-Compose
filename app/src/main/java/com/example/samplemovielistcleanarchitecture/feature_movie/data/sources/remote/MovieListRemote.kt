package com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.remote

import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseResult
import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseFailedType
import com.example.samplemovielistcleanarchitecture.core.utils.AppConstants
import com.example.samplemovielistcleanarchitecture.core.utils.NetworkUtils
import com.example.samplemovielistcleanarchitecture.core.network.MoviesApiService
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.remote.MovieListApiResponseDto
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MovieListRemote(
    private val api: MoviesApiService, private val networkUtils: NetworkUtils
) {

    suspend fun getMoviesList() = flow<RemoteResponseResult<MovieListApiResponseDto>> {
        if (networkUtils.isNetworkAvailable()) {
            try {
                val response = api.fetchMoviesList()
                return@flow emit(parseMovieListResponse(response))
            }catch (e: Exception) {
                emit(
                    RemoteResponseResult.Failed(
                        RemoteResponseFailedType.NO_INTERNET, AppConstants.PLEASE_CHECK_CONNECTION
                    )
                )
            }
        } else {
            emit(
                RemoteResponseResult.Failed(
                    RemoteResponseFailedType.NO_INTERNET, AppConstants.PLEASE_CHECK_CONNECTION
                )
            )
        }
    }

    private fun parseMovieListResponse(response: MovieListApiResponseDto?): RemoteResponseResult<MovieListApiResponseDto> {
        if (response != null) {
            try {
                if (response.results == null) {
                    return RemoteResponseResult.Failed(
                        RemoteResponseFailedType.DATA_ERROR, AppConstants.SOMETHING_WENT_WRONG
                    )
                }
                return RemoteResponseResult.Success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                return RemoteResponseResult.Failed(
                    RemoteResponseFailedType.DATA_ERROR, AppConstants.SOMETHING_WENT_WRONG
                )
            }
        } else {
            return RemoteResponseResult.Failed(
                RemoteResponseFailedType.SERVER_ERROR, AppConstants.SOMETHING_WENT_WRONG
            )
        }
    }

}