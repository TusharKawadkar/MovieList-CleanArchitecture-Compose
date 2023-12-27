package com.example.samplemovielistcleanarchitecture.ui.composables.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInQuint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.samplemovielistcleanarchitecture.ui.composables.components.SplashScreenAnimImageLayout
import com.example.samplemovielistcleanarchitecture.ui.theme.Purple80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun SplashScreen(navigateToNextScreen: () -> Unit = {}) {
    Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
        val titleAnimationState = remember {
            Animatable(0f)
        }
        val textAnimationState = remember {
            Animatable(0f)
        }

        SplashScreenAnimImageLayout()
        IntroText(titleAnimationState, textAnimationState)

        LaunchedEffect(Unit) {
            launch {
                delay(3500)
                titleAnimationState.animateTo(
                    1f, animationSpec = tween(500, easing = EaseInQuint)
                )
                delay(100)
                textAnimationState.animateTo(
                    1f, animationSpec = tween(500, easing = LinearEasing)
                )
                delay(2000)
                navigateToNextScreen()
            }
        }
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
                text = "Sample Movie App",
                color = Purple80,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                modifier = Modifier
                    .fillMaxWidth()
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
