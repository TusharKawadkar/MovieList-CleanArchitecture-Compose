package com.example.samplemovielistcleanarchitecture.feature_movie.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplemovielistcleanarchitecture.core.network.dto.CommonFailureType
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases.GetMovieList
import com.example.samplemovielistcleanarchitecture.feature_movie.domain.usecases.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private var movieListJob: Job? = null

    init {
        observeMovieListStates()
    }

    private fun observeMovieListStates() {
        viewModelScope.launch {
            movieUseCase.getMovie()
                .onEach {
                    when(it){
                        is GetMovieList.States.Fetching -> {
                            setIndicationAsLoading()
                        }
                        is GetMovieList.States.Loaded -> {
                            setIndicationAsClearAll()
                            _movieListState.value = it.data
                        }
                        is GetMovieList.States.Failed -> {
                            setIndicationAsFailed(it.type, it.message)
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun setIndicationAsLoading() {
        _indicationState.value = IndicationStates.Loading
    }

    private fun setIndicationAsClearAll() {
        _indicationState.value = IndicationStates.ClearAll
    }

    private fun setIndicationAsFailed(type: CommonFailureType, msg: String) {
        _indicationState.value = IndicationStates.Failed(type, msg)
    }

    sealed class IndicationStates {
        data object Loading : IndicationStates()
        data object ClearAll : IndicationStates()
        data class Failed(val type: CommonFailureType, val message: String) :
            IndicationStates()
    }

}