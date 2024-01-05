package com.example.samplemovielistcleanarchitecture.feature_movie.presentation.composables.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.samplemovielistcleanarchitecture.core.repository.AppRepository
import com.example.samplemovielistcleanarchitecture.feature_movie.data.models.local.MovieItemEntity
import kotlinx.coroutines.launch

@Preview
@Composable
fun MovieListScreen(navigateToNextScreen: () -> Unit = {}) {
    val movieListState =
        AppRepository.getInstance().movieListUseCase!!.movieListFlows.collectAsState(initial = emptyList())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(modifier = Modifier.matchParentSize()) {
            items(movieListState.value) {
                MovieItemUI(it)
            }
        }
        val swipeDownAminState = remember {
            Animatable(0.8f)
        }
        Text(
            text = "Swipe down\nfor more",
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .scale(swipeDownAminState.value)
        )
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
    }
}

val testMovieItem = MovieItemEntity(
    1,
    "en",
    "The Hunger Games: The Ballad of Songbirds",
    "64 years before he becomes the tyrannical president of Panem, Coriolanus Snow sees a chance for a change in fortunes when he mentors Lucy Gray Baird, the female tribute from District 12.",
    200,
    "2023-11-15",
    "/5a4JdoFwll5DRtKMe7JLuGQ9yJm.jpg",
    false
)

@Preview
@Composable
fun MovieItemUI(movieItem: MovieItemEntity = testMovieItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenHeightDp.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 2)
        ) {
            val animState = remember {
                androidx.compose.animation.core.Animatable(1f)
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
                        translationX = animState.value
                        translationY = animState.value
                    }
                    .clip(RectangleShape)
                    .clipToBounds()
                    .background(Color.Black)
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
            val alphaState = remember {
                androidx.compose.animation.core.Animatable(0f)
            }
            LaunchedEffect(Unit) {
                launch {
                    alphaState.animateTo(
                        1f,
                        animationSpec = tween(
                            1000,
                            easing = EaseInQuad
                        )
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                text = "${movieItem.release_date}",
                style = MaterialTheme.typography.overline,
                color = Color.White
            )
            Text(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .alpha(alpha = alphaState.value),
                text = movieItem.original_title,
                color = Color.Red,
                style = MaterialTheme.typography.h6
            )
            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .alpha(alpha = alphaState.value),
                text = movieItem.overview,
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
            Text(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .alpha(alpha = alphaState.value),
                text = "Language: ${movieItem.original_language} ",
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .alpha(alpha = alphaState.value),
                text = "${movieItem.vote_count} votes",
                style = MaterialTheme.typography.subtitle2,
                color = Color.Red
            )
        }
    }
}