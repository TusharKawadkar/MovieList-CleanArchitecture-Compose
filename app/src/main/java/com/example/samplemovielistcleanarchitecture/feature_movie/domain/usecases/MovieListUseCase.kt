package com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseFailedType
import com.example.samplemovielistcleanarchitecture.core.network.dto.RemoteResponseResult
import com.example.samplemovielistcleanarchitecture.core.utils.AppConstants
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.remote.MovieListItemDto
import com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist.MovieListRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieListUseCase(
    private val movieListRepository: MovieListRepositoryImpl
) {

    private val _indicationState = mutableStateOf<IndicationStates>(IndicationStates.Loading)
    val indicationUiState: State<IndicationStates>
        get() = _indicationState

    var movieListFlows = flow<List<MovieItemEntity>> {}

    init {
        movieListFlows = movieListRepository.observeMovieList().map {
            if (it.isEmpty()) {
                loadFromRemote()
            } else {
                setIndicationAsClearAll()
            }
            it
        }
    }

    fun loadFromRemote() {
        setIndicationAsLoading()
        CoroutineScope(Dispatchers.IO).launch {
            movieListRepository.callMovieListApi().collect {
                val response = it
                if (response.isSuccess()) {
                    setIndicationAsClearAll()
                    updateRemoteDataToDb(response.parseToSuccessClass().t.results!!)
                } else {
                    if (response is RemoteResponseResult.Failed) {
                        when (response.type) {
                            RemoteResponseFailedType.NO_INTERNET -> {
                                setIndicationAsFailed(
                                    IndicationStatesFailedType.NO_INTERNET,
                                    AppConstants.PLEASE_CHECK_CONNECTION
                                )
                            }

                            RemoteResponseFailedType.DATA_ERROR -> {
                                setIndicationAsFailed(
                                    IndicationStatesFailedType.DATA_ERROR,
                                    AppConstants.SOMETHING_WENT_WRONG
                                )
                            }

                            RemoteResponseFailedType.SERVER_ERROR -> {
                                setIndicationAsFailed(
                                    IndicationStatesFailedType.SERVER_ERROR,
                                    AppConstants.SOMETHING_WENT_WRONG
                                )
                            }

                            else -> {
                                setIndicationAsFailed(
                                    IndicationStatesFailedType.SERVER_ERROR,
                                    AppConstants.SOMETHING_WENT_WRONG
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateRemoteDataToDb(list: List<MovieListItemDto>) {
        movieListRepository.updateRemoteDataToDb(list)
    }

    private fun setIndicationAsLoading() {
        _indicationState.value = IndicationStates.Loading
    }

    private fun setIndicationAsClearAll() {
        _indicationState.value = IndicationStates.ClearAll
    }

    private fun setIndicationAsFailed(type: IndicationStatesFailedType, msg: String) {
        _indicationState.value = IndicationStates.Failed(type, msg)
    }

    sealed class IndicationStates {
        object Loading : IndicationStates()
        object ClearAll : IndicationStates()
        data class Failed(val type: IndicationStatesFailedType, val message: String) :
            IndicationStates()
    }

    enum class IndicationStatesFailedType {
        NO_INTERNET, SERVER_ERROR, DATA_ERROR
    }

}