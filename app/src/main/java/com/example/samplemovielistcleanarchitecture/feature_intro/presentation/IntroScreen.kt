package com.example.samplemovielistcleanarchitecture.feature_intro.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.samplemovielistcleanarchitecture.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.remember as remember

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun SplashScreen(navigateToNextScreen: () -> Unit = {}) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val titleAnimationState = remember {
            Animatable(0f)
        }
        val textAnimationState = remember {
            Animatable(0f)
        }
        val canvasAnimationState = remember {
            Animatable(0f)
        }

        IntroBackgroundAnimLayout()
        IntroText(titleAnimationState, textAnimationState)
        MovieReelStripAnimation(canvasAnimationState)

        LaunchedEffect(Unit) {
            launch {
                delay(3500)
                canvasAnimationState.animateTo(
                    1f, animationSpec = tween(
                        3000, easing = EaseInCirc
                    )
                )
                titleAnimationState.animateTo(
                    1f, animationSpec = tween(
                        500
                    )
                )
                delay(100)
                textAnimationState.animateTo(
                    1f, animationSpec = tween(
                        500
                    )
                )
                delay(3000)
                navigateToNextScreen()
            }
        }
    }
}

@Composable
fun MovieReelStripAnimation(
    canvasAnimationState: Animatable<Float, AnimationVector1D> = Animatable(
        1f
    )
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            alignment = Alignment.TopStart,
            painter = painterResource(id = R.drawable.moviereel),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 100.dp)
                .scale(canvasAnimationState.value)
                .offset(x = -500.dp + (1000.dp*canvasAnimationState.value))
        )
        Image(
            alignment = Alignment.BottomStart,
            painter = painterResource(id = R.drawable.moviereel),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 100.dp)
                .scale(canvasAnimationState.value)
                .offset(x = 500.dp - (1000.dp*canvasAnimationState.value))
        )
    }
}

@Composable
fun IntroText(
    titleAnimationState: Animatable<Float, AnimationVector1D>,
    textAnimationState: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Sample",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(titleAnimationState.value)
            )
            Text(
                text = "Movie App",
                color = Color.Red,
                textAlign = TextAlign.Center,
                fontSize = 60.sp,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .scale(titleAnimationState.value)
            )
            Text(
                text = "Clean code architecture",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .scale(textAnimationState.value)
            )
            Text(
                text = "Jetpack compose",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .scale(textAnimationState.value)
            )
            Text(
                text = "Offline first",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .scale(textAnimationState.value)
            )
        }
    }
}
