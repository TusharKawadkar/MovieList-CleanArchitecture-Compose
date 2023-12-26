package com.example.samplemovielistcleanarchitecture.ui.composables.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.example.samplemovielistcleanarchitecture.ui.composables.components.SplashScreenAnimImageLayout
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun SplashScreen(navigateTo: () -> Unit = {}) {
    Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
        SplashScreenAnimImageLayout()
        DimScreen()
        //IntroInfoGraphics()
    }
}

@Composable
fun DimScreen() {
    val alphaAnimation = remember {
        Animatable(0f)
    }
    Spacer(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            alpha = alphaAnimation.value
        }
        .background(Color.Black))
    LaunchedEffect(Unit) {
        delay(2000)
        alphaAnimation.animateTo(0.8f, animationSpec = tween(600, easing = LinearEasing))
    }
}
