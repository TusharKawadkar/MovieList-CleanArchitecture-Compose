package com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases

import com.example.samplemovielistcleanarchitecture.core.network.dto.CommonFailureType
import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseResult
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
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

class GetMovieList @Inject constructor(private val repository: MovieListRepository) {

    sealed class States {
        data object Fetching : States()
        data class Loaded(val data: List<MovieItemEntity>) : States()
        data class Failed(val type: CommonFailureType, val message: String) : States()
    }

    operator fun invoke() = flow<States> {
        repository.observeMovieList().collect {
            if (it.isEmpty()) {
                emit(States.Fetching)
                repository.getMoviesListRemote().collect { result ->
                    when (result) {
                        is RemoteResponseResult.Success -> {
                            repository.updateRemoteDataToDb(result.t.results!!)
                            emit(States.Fetching)
                        }

                        is RemoteResponseResult.Failed -> {
                            emit(States.Failed(result.type, result.message))
                        }
                    }
                }
            } else {
                emit(States.Loaded(it))
            }
        }
        /*return repository.observeMovieList().map {
            if (it.isEmpty()) {
                repository.getMoviesListRemote().collect { result ->
                    when (result) {
                        is RemoteResponseResult.Success -> {
                            repository.updateRemoteDataToDb(result.t.results!!)
                            States.Fetching
                        }

                        is RemoteResponseResult.Failed -> {
                            States.Failed(result.type, result.message)
                        }
                    }
                }
                States.Fetching
            } else {
                States.Loaded(it)
            }
        }*/
    }
}