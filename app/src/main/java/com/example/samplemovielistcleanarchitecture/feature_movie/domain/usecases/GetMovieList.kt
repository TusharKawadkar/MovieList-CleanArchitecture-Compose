package com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases

import com.example.samplemovielistcleanarchitecture.core.network.dto.CommonFailureType
import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseResult
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.remote.MovieListApiResponseDto
import com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist.MovieListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMovieList(private val repository: MovieListRepository) {

    sealed class States {
        data object Fetching : States()
        data class Loaded(val data: List<MovieItemEntity>) : States()
        data class Failed(val type: CommonFailureType, val message: String) : States()
    }

    operator fun invoke() = flow<States> {
        repository.observeMovieList().collect {
            if (it.isEmpty()) {
                emit(States.Fetching)
                when (val result: RemoteResponseResult<MovieListApiResponseDto> = repository.getMoviesListRemote()) {
                    is RemoteResponseResult.Success -> {
                        repository.updateRemoteDataToDb(result.t.results!!)
                    }

                    is RemoteResponseResult.Failed -> {
                        emit(States.Failed(result.type, result.message))
                    }
                }
            } else {
                emit(States.Loaded(it))
            }
        }
    }
}