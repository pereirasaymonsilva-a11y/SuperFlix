// ui/navigation/NavGraph.kt
package com.superflix.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.superflix.app.data.models.MediaType
import com.superflix.app.ui.screens.details.DetailsScreen
import com.superflix.app.ui.screens.home.HomeScreen
import com.superflix.app.ui.screens.library.LibraryScreen
import com.superflix.app.ui.screens.player.PlayerScreen
import com.superflix.app.ui.screens.profile.ProfileScreen
import com.superflix.app.ui.screens.search.SearchScreen

@Composable
fun NavGraph(navController = rememberNavController()) {
    val context = LocalContext.current
    
    NavHost(
        navController = navController,
        startDestination = Destinations.Home.route
    ) {
        composable(Destinations.Home.route) {
            HomeScreen(
                context = context,
                onMovieClick = { id, type ->
                    navController.navigate(Destinations.Details.passArguments(id, type.name))
                }
            )
        }
        
        composable(Destinations.Search.route) {
            SearchScreen(
                context = context,
                onResultClick = { id, type ->
                    navController.navigate(Destinations.Details.passArguments(id, type.name))
                }
            )
        }
        
        composable(Destinations.Library.route) {
            LibraryScreen(
                context = context,
                onItemClick = { id, type ->
                    navController.navigate(Destinations.Details.passArguments(id, type.name))
                }
            )
        }
        
        composable(Destinations.Profile.route) {
            ProfileScreen()
        }
        
        composable(
            route = Destinations.Details.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: return@composable
            val type = MediaType.valueOf(backStackEntry.arguments?.getString("type") ?: "MOVIE")
            
            DetailsScreen(
                movieId = movieId,
                mediaType = type,
                onPlayClick = {
                    navController.navigate(Destinations.Player.passArguments(movieId))
                }
            )
        }
        
        composable(
            route = Destinations.Player.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: return@composable
            PlayerScreen(movieId = movieId)
        }
    }
}