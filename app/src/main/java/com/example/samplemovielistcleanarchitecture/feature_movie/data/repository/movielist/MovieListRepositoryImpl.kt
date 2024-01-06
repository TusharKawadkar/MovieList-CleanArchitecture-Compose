package com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist

import com.example.samplemovielistcleanarchitecture.core.network.dto.CommonFailureType
import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseResult
import com.example.samplemovielistcleanarchitecture.core.utils.AppConstants
import com.example.samplemovielistcleanarchitecture.core.utils.NetworkUtils
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.remote.MovieListApiResponseDto
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.remote.MovieListItemDto
import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.local.MoviesDao
import com.example.samplemovielistcleanarchitecture.feature_movie.data.sources.remote.MoviesApiService
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MovieListRepositoryImpl(
    @Inject private val dao: MoviesDao,
    @Inject private val api: MoviesApiService,
    @Inject private val networkUtils: NetworkUtils
) : MovieListRepository {

    override suspend fun observeMovieList() = dao.observeMovieList()

    override suspend fun updateRemoteDataToDb(movieList: List<MovieListItemDto>) {
        val dataList = movieList.map {
            MovieItemEntity(
                it.id,
                it.original_language,
                it.original_title,
                it.overview,
                it.vote_count,
                it.release_date,
                it.poster_path
            )
        }
        repeat(dataList.size) {
            dao.addMoviesToDb(dataList[it])
        }
    }

    override suspend fun getMoviesListRemote() =
        flow<RemoteResponseResult<MovieListApiResponseDto>> {
            if (networkUtils.isNetworkAvailable()) {
                try {
                    val response = api.fetchMoviesList()
                    return@flow emit(parseMovieListResponse(response))
                } catch (e: Exception) {
                    emit(
                        RemoteResponseResult.Failed(
                            CommonFailureType.NO_INTERNET, AppConstants.PLEASE_CHECK_CONNECTION
                        )
                    )
                }
            } else {
                emit(
                    RemoteResponseResult.Failed(
                        CommonFailureType.NO_INTERNET, AppConstants.PLEASE_CHECK_CONNECTION
                    )
                )
            }
        }

    private fun parseMovieListResponse(response: MovieListApiResponseDto?): RemoteResponseResult<MovieListApiResponseDto> {
        return response?.let {
            try {
                it.results?.let {
                    return RemoteResponseResult.Success(response)
                } ?: return RemoteResponseResult.Failed(
                    CommonFailureType.DATA_ERROR, AppConstants.SOMETHING_WENT_WRONG
                )
            } catch (e: Exception) {
                e.printStackTrace()
                return RemoteResponseResult.Failed(
                    CommonFailureType.DATA_ERROR, AppConstants.SOMETHING_WENT_WRONG
                )
            }
        } ?: RemoteResponseResult.Failed(
            CommonFailureType.SERVER_ERROR, AppConstants.SOMETHING_WENT_WRONG
        )
    }
}

