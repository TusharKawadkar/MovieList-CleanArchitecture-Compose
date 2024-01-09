package com.example.samplemovielistcleanarchitecture.feature_movie.presentation

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplemovielistcleanarchitecture.core.network.dto.CommonFailureType
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases.GetMovieList
import com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases.MovieUseCase
import com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases.RefreshMovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _indicationState = mutableStateOf<IndicationStates>(IndicationStates.Loading)
    val indicationUiState: State<IndicationStates>
        get() = _indicationState

    private val _movieListState = mutableStateOf(emptyList<MovieItemEntity>())
    val movieListState: State<List<MovieItemEntity>>
        get() = _movieListState

    private var refreshJob: Job? = null

    init {
        observeMovieListStates()
    }

    fun onEvent(movieEvent: MovieEvent) {
        when (movieEvent) {
            MovieEvent.Refresh -> {
                clearRefreshJob()
                refreshJob = viewModelScope.launch(Dispatchers.IO) {
                    refreshMovielist(this)
                }
            }
        }
    }

    private suspend fun refreshMovielist(coroutineScope: CoroutineScope) {
        coroutineScope.run {
            movieUseCase.refreshMovieList().buffer().collect { state ->
                when (state) {
                    is RefreshMovieList.States.Fetching -> {
                        setIndicationAsLoading()
                    }

                    is RefreshMovieList.States.Loaded -> {
                        clearRefreshJob()
                    }

                    is RefreshMovieList.States.Failed -> {
                        //delay(1000)
                        setIndicationAsFailed(state.type, state.message)
                    }
                }
            }
        }
    }

    private fun clearRefreshJob() {
        refreshJob?.cancel()
        refreshJob = null
    }

    private fun observeMovieListStates() {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.getMovie()
                .collect { state ->
                    when (state) {
                        is GetMovieList.States.Fetching -> {
                            setIndicationAsLoading()
                        }

                        is GetMovieList.States.Loaded -> {
                            setIndicationAsClearAll()
                            viewModelScope.launch {
                                _movieListState.value = state.data
                            }
                        }

                        is GetMovieList.States.Failed -> {
                            setIndicationAsFailed(state.type, state.message)
                        }
                    }
                }
        }
    }

    private fun setIndicationAsLoading() {
        viewModelScope.launch {
            _indicationState.value = IndicationStates.Loading
        }
    }

    private fun setIndicationAsClearAll() {
        viewModelScope.launch {
            _indicationState.value = IndicationStates.ClearAll
        }
    }

    private fun setIndicationAsFailed(type: CommonFailureType, msg: String) {
        viewModelScope.launch {
            _indicationState.value = IndicationStates.Failed(type, msg)
        }
    }

    @Stable
    sealed class IndicationStates {
        data object Loading : IndicationStates()
        data object ClearAll : IndicationStates()
        data class Failed(val type: CommonFailureType, val message: String) :
            IndicationStates()
    }

    @Stable
    sealed class MovieEvent {
        data object Refresh : MovieEvent()
    }

}