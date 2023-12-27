package com.example.samplemovielistcleanarchitecture.ui.composables.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.samplemovielistcleanarchitecture.core.repository.AppRepository
import com.example.samplemovielistcleanarchitecture.data.local.entities.MovieItemEntity

@Preview
@Composable
fun MovieListScreen(navigateToNextScreen: () -> Unit = {}) {
    val movieListState =
        AppRepository.getInstance().movieListUseCase!!.movieListFlows.collectAsState(initial = emptyList())
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(movieListState.value) {
                MovieItemUI(it)
            }
        }
    }
}

@Composable
fun MovieItemUI(movieItem: MovieItemEntity) {
    Text(text = movieItem.original_title)
}