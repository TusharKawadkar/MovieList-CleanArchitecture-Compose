package com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases

import android.util.Log
import com.example.samplemovielistcleanarchitecture.core.network.dto.CommonFailureType
import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseResult
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.remote.MovieListApiResponseDto
import com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist.MovieListRepository
import kotlinx.coroutines.flow.flow

class RefreshMovieList (private val repository: MovieListRepository) {

    sealed class States {
        data object Fetching : States()
        data object Loaded: States()
        data class Failed(val type: CommonFailureType, val message: String) : States()
    }

    suspend operator fun invoke() = flow<States> {
        emit(States.Fetching)
        when (val result: RemoteResponseResult<MovieListApiResponseDto> = repository.getMoviesListRemote()) {
            is RemoteResponseResult.Success -> {
                emit(States.Loaded)
                repository.updateRemoteDataToDb(result.t.results!!)
            }

            is RemoteResponseResult.Failed -> {
                emit(States.Failed(result.type, result.message))
            }
        }
    }
}