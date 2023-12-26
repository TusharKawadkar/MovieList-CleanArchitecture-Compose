package com.example.samplemovielistcleanarchitecture.ui.composables.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.EaseOutSine
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.samplemovielistcleanarchitecture.R
import com.example.samplemovielistcleanarchitecture.domain.usecases.SplashScreenUseCase
import kotlinx.coroutines.launch

@Preview
@Composable
fun SplashScreenAnimImageLayout() {
    val scrollState = rememberLazyListState()
    val scrollStateReversed = rememberLazyListState()
    val splashScreenUseCase = SplashScreenUseCase()
    val screenResListForward = splashScreenUseCase.getImageResList()
    val screenResListReversed = splashScreenUseCase.getImageResListReversed()
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn (
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    shape = traingleDown
                    clip = true
                }
        ){

            items(screenResListForward.size) {
                Image(
                    painter = painterResource(id = screenResListForward[it]),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }

        LazyColumn (
            state = scrollStateReversed,
            reverseLayout = true,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    shape = traingleUp
                    clip = true
                }
        ){
            items(screenResListReversed.size) {
                Image(
                    painter = painterResource(id = screenResListReversed[it]),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }
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
    }
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
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 5000, easing = LinearEasing)
) {
    var previousValue = 0f
    scroll(MutatePriority.PreventUserInput) {
        animate(0f, 500f, animationSpec = animationSpec) { currentValue, _ ->
            previousValue += scrollBy(currentValue - previousValue)
        }
    }
}

