package com.example.samplemovielistcleanarchitecture.ui.composables.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.EaseInQuint
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.samplemovielistcleanarchitecture.ui.composables.components.SplashScreenAnimImageLayout
import com.example.samplemovielistcleanarchitecture.ui.theme.Purple80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.tan
import androidx.compose.runtime.remember as remember

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
        val canvasAnimationState = remember {
            Animatable(0f)
        }

        SplashScreenAnimImageLayout()
        IntroText(titleAnimationState, textAnimationState, canvasAnimationState)

        LaunchedEffect(Unit) {
            launch {
                delay(3500)
                canvasAnimationState.animateTo(
                    1f, animationSpec = tween(
                        1500, easing = EaseOutBounce
                    )
                )
                titleAnimationState.animateTo(
                    1f, animationSpec = tween(
                        500,
                        easing = LinearEasing

                    )
                )
                delay(100)
                textAnimationState.animateTo(
                    1f, animationSpec = tween(
                        500,
                        easing = LinearEasing
                    )
                )
                delay(3000)
                navigateToNextScreen()
            }
        }
    }
}

@Composable
fun IntroText(
    titleAnimationState: Animatable<Float, AnimationVector1D>,
    textAnimationState: Animatable<Float, AnimationVector1D>,
    canvasAnimationState: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val lineColor = Purple80
            val strokeWidth = 25.dp.toPx()
            val canvasHeight = size.height
            val canvasWidth = size.width
            val screenRatio = canvasHeight / canvasWidth

            //Line1
            val startPosition1 = Offset(0f, canvasHeight)

            val boundPosition1 = getBoundsOffset(center.x / 2, center.y, screenRatio)
            val drawPointer1 = getDrawerOffset1(boundPosition1, center, canvasAnimationState.value)

            drawLine(
                color = lineColor,
                start = startPosition1,
                end = drawPointer1,
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )

            //Line2
            val startPosition2 = Offset(canvasWidth, 0f)
            val boundPosition2 = getBoundsOffset(center.x + (center.x / 2), -center.y, screenRatio)
            val drawPointer2 = getDrawerOffset2(boundPosition2, center, canvasAnimationState.value)

            drawLine(
                color = lineColor,
                start = startPosition2,
                end = drawPointer2,
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }
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
                color = Purple80,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
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

fun getBoundsOffset(newX: Float, centerY: Float, ratio: Float): Offset {
    return Offset(newX, centerY + (newX * ratio))
}

fun getDrawerOffset1(bounds: Offset, center: Offset, scale: Float = 1f): Offset {
    val ratio = center.y / center.x
    val yMax = center.y * 2
    val newX = bounds.x * scale
    return Offset(newX, yMax - (newX * ratio))
}

fun getDrawerOffset2(bounds: Offset, center: Offset, scale: Float = 1f): Offset {
    val ratio = center.y / center.x
    val xMax = center.x * 2
    val diffX = xMax - bounds.x
    val newX = xMax - (diffX * scale)

    val yMax = center.y * 2
    return Offset(newX, yMax - (newX * ratio))
}

