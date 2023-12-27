package com.example.samplemovielistcleanarchitecture.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.samplemovielistcleanarchitecture.ui.composables.screens.SplashScreen
import com.example.samplemovielistcleanarchitecture.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ApplicationScreen()
            }
        }
    }
}

@Preview
@Composable
fun ApplicationScreen() {
    val navigationController = rememberNavController()
    AppNavigation(navigationController)
}

@Composable
fun AppNavigation(navigationController: NavHostController) {
    Scaffold(
        modifier = Modifier,
        /*bottomBar = {
            BottomNavigation {
            }
        }*/
    ) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = "landing",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LandingPageGraph(navController = navigationController)
            MainGraph(navController = navigationController)
        }
    }
}

fun NavGraphBuilder.LandingPageGraph(navController: NavController) {
    navigation(startDestination = "screen_splash", route = "landing") {
        composable("screen_splash") {
            SplashScreen {
                navController.navigate("main")
            }
        }
    }
}

fun NavGraphBuilder.MainGraph(navController: NavController) {
    navigation(startDestination = "screen_movie_list", route = "main") {
        composable("screen_movie_list") {
            navController.navigate("main")
        }
        composable("screen_favourites") {

        }
    }
}



