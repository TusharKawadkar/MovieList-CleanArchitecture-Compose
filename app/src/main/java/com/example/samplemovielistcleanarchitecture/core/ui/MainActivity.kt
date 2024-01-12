package com.example.samplemovielistcleanarchitecture.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.samplemovielistcleanarchitecture.feature_movie.presentation.MovieListScreen
import com.example.samplemovielistcleanarchitecture.feature_intro.presentation.SplashScreen
import com.example.samplemovielistcleanarchitecture.core.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    Scaffold(
        modifier = Modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = "landing",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LandingPageGraph(navController = navigationController)
            MainGraph(navController = navigationController, snackbarHostState, coroutineScope)
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

fun NavGraphBuilder.MainGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    navigation(startDestination = "screen_movie_list", route = "main") {
        composable("screen_movie_list") {
            MovieListScreen() {errorMessage ->
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
            }
        }
    }
}



