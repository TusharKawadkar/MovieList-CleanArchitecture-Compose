package com.example.samplemovielistcleanarchitecture.ui.composables.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.samplemovielistcleanarchitecture.R

@Preview
@Composable
fun IntroInfoGraphics(){
    Box(modifier = Modifier.fillMaxSize()) {
        val circuitAnimation = remember {
            Animatable(0f)
        }
        Image(
            painter = painterResource(id = R.drawable.circuit1),
            contentDescription = null,
            modifier = Modifier
        )
        Box(modifier = Modifier)
        /*LaunchedEffect(Unit) {
            delay(2000)
            circuitAnimation.animateTo(0.8f, animationSpec = tween(800))
        }*/
    }
}

private val ShapeCircuit = GenericShape { size, _ ->
    val spacer = 10f
    moveTo(0f, size.height)
    lineTo(0f, size.height)
    lineTo(0f, 0f)
}