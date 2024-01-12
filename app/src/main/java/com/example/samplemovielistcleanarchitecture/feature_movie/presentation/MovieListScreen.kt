package com.example.samplemovielistcleanarchitecture.feature_movie.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.samplemovielistcleanarchitecture.R
import com.example.samplemovielistcleanarchitecture.core.ui.theme.Purple40
import com.example.samplemovielistcleanarchitecture.core.ui.theme.Purple80
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import com.example.samplemovielistcleanarchitecture.feature_movie.data.repository.movielist.FakeMovieListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MovieListScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    showSnackbar: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val loadingAnimState = remember {
            Animatable(0f)
        }
        val coroutineScope = rememberCoroutineScope()

        IndicationState(
            vieModel = viewModel,
            loadingAnimState = loadingAnimState,
            coroutineScope = coroutineScope,
            showSnackbar = showSnackbar
        )
        MovieListUi(viewModel, loadingAnimState) {
            viewModel.onEvent(MovieViewModel.MovieEvent.Refresh)
        }
    }
}


@Composable
fun IndicationState(
    vieModel: MovieViewModel,
    loadingAnimState: Animatable<Float, AnimationVector1D>,
    coroutineScope: CoroutineScope,
    showSnackbar: (String) -> Unit
) {
    val currentState = rememberUpdatedState(newValue = vieModel.indicationUiState.value)
    LaunchedEffect(currentState.value) {
        when (currentState.value) {
            is MovieViewModel.IndicationStates.Loading -> {
                coroutineScope.launch {
                    loadingAnimState.snapTo(0f)
                    loadingAnimState.animateTo(
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(animation = tween(500))
                    )
                }
            }

            is MovieViewModel.IndicationStates.ClearAll -> {
                coroutineScope.launch {
                    loadingAnimState.stop()
                }
            }

            is MovieViewModel.IndicationStates.Failed -> {
                coroutineScope.launch {
                    loadingAnimState.stop()
                    showSnackbar((currentState.value as MovieViewModel.IndicationStates.Failed).message)
                }
            }
        }
    }
}

@Composable
fun BoxScope.MovieListUi(
    viewModel: MovieViewModel,
    loadingAnimState: Animatable<Float, AnimationVector1D>,
    onRefresh: () -> Unit
) {
    val movieListState = viewModel.movieListState.value
    val swipeDownAminState = remember {
        Animatable(0.8f)
    }
    LaunchedEffect(Unit) {
        launch {
            swipeDownAminState.animateTo(
                1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        500,
                        easing = EaseInQuad
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    LazyColumn(modifier = Modifier.matchParentSize()) {
        this.items(movieListState.size) {
            MovieItemUI(movieListState[it])
        }
    }
    Image(
        painter = painterResource(id = R.drawable.baseline_refresh_24),
        contentDescription = null,
        modifier = Modifier
            .align(Alignment.TopEnd)
            .clickable {
                onRefresh()
            }
            .padding(all = 16.dp)
            .graphicsLayer {
                rotationZ = loadingAnimState.value
            }
    )
    Text(
        text = "Swipe down\nfor more",
        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = 10.sp,
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .scale(swipeDownAminState.value)
    )
}

@Composable
fun MovieItemUI(movieItem: MovieItemEntity = FakeMovieListRepository.testMovieItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenHeightDp.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 2)
                .clip(MaterialTheme.shapes.medium)
        ) {
            val animState = remember {
                Animatable(1f)
            }
            LaunchedEffect(Unit) {
                launch {
                    animState.animateTo(
                        1.5f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                5000,
                                easing = EaseInQuad
                            ),
                            repeatMode = RepeatMode.Reverse
                        )
                    )
                }
            }
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original${movieItem.poster_path}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        scaleX = animState.value
                        scaleY = animState.value
                    }
                    .clip(RectangleShape)
                    .clipToBounds()
                    .background(Color.Black)
                    .animateContentSize()
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.Transparent),
                        )
                    )
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black)
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(start = 12.dp, end = 12.dp, top = 12.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                text = "${movieItem.release_date}",
                style = MaterialTheme.typography.overline,
                color = Color.Gray
            )
            Text(
                modifier = Modifier
                    .padding(top = 24.dp),
                text = movieItem.original_title,
                color = Purple40,
                style = MaterialTheme.typography.h6
            )
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = movieItem.overview,
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
            Text(
                modifier = Modifier
                    .padding(top = 40.dp),
                text = "Language: ${movieItem.original_language} ",
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = "${movieItem.vote_count} votes",
                style = MaterialTheme.typography.subtitle2,
                color = Purple80
            )
        }
    }
}