package com.devspacecinenow

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CineNowApp(modifier: Modifier = Modifier) {
    val navControlle = rememberNavController()
    NavHost(navController = navControlle, startDestination = "movieList" ){
        composable(route = "movieList" ){
            MovieListScreen(navControlle)
        }
        composable(route = "movieDetail" ){
            MovieDetailScreen()
        }
    }
}