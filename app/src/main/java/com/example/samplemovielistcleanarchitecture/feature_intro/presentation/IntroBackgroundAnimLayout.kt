package com.example.samplemovielistcleanarchitecture.feature_intro.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun IntroBackgroundAnimLayout(viewModel: IntroScreenViewModel = hiltViewModel()) {
    val scrollState = rememberLazyListState()
    val scrollStateReversed = rememberLazyListState()
    val screenResListForward = viewModel.slideImagesState1.value
    val screenResListReversed = viewModel.slideImagesState2.value
    val dimScreenAnimationState = remember {
        Animatable(0f)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(state = scrollState, modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                shape = traingleDown
                clip = true
            }) {

            items(screenResListForward.size) {
                Image(
                    painter = painterResource(id = screenResListForward[it].imgRes),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }

        LazyColumn(state = scrollStateReversed,
            reverseLayout = true,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    shape = traingleUp
                    clip = true
                }) {
            items(screenResListReversed.size) {
                Image(
                    painter = painterResource(id = screenResListReversed[it].imgRes),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }
        DimScreen(alphaAnimation = dimScreenAnimationState)
        LaunchedEffect(key1 = Unit) {
            launch {
                while (true) {
                    scrollState.autoScroll()
                }
            }
        }
        LaunchedEffect(key1 = Unit) {
            launch {
                while (true) {
                    scrollStateReversed.autoScroll()
                }
            }
        }
        LaunchedEffect(Unit) {
            launch {
                delay(2500)
                dimScreenAnimationState.animateTo(
                    0.85f, animationSpec = tween(
                        800
                    )
                )
            }
        }
    }
}

@Composable
fun DimScreen(alphaAnimation: Animatable<Float, AnimationVector1D>) {
    Spacer(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            alpha = alphaAnimation.value
        }
        .background(Color.Black))
}

private val traingleUp = GenericShape { size, _ ->
    moveTo(size.width, 0f)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)
}

private val traingleDown = GenericShape { size, _ ->
    moveTo(size.width, 0f)
    lineTo(0f, size.height)
    lineTo(0f, 0f)
}

suspend fun ScrollableState.autoScroll(
    animationSpec: AnimationSpec<Float> = tween(
        durationMillis = 12000, easing = LinearEasing
    )
) {
    var previousValue = 0f
    scroll(MutatePriority.PreventUserInput) {
        animate(0f, 600f, animationSpec = animationSpec) { currentValue, _ ->
            previousValue += scrollBy(currentValue - previousValue)
        }
    }
}

