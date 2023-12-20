package com.example.samplemovielistcleanarchitecture.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.samplemovielistcleanarchitecture.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                ApplicationScreen()
            }
        }
    }
}

@Preview
@Composable
fun ApplicationScreen() {
    val navigationController = rememberNavController()
    Scaffold(modifier = Modifier, bottomBar = {
        BottomNavigation {

        }
    }) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = "",
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}

