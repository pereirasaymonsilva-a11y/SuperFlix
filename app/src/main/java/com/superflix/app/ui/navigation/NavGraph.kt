// ui/navigation/NavGraph.kt
package com.superflix.app.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.superflix.app.data.models.MediaType
import com.superflix.app.ui.screens.details.DetailsScreen
import com.superflix.app.ui.screens.home.HomeScreen
import com.superflix.app.ui.screens.library.LibraryScreen
import com.superflix.app.ui.screens.profile.ProfileScreen
import com.superflix.app.ui.screens.search.SearchScreen

@Composable
fun NavGraph(context: Context) {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                context = context,
                onMovieClick = { id, type ->
                    navController.navigate("details/$id/${type.name}")
                }
            )
        }
        
        composable("search") {
            SearchScreen(
                context = context,
                onResultClick = { id, type ->
                    navController.navigate("details/$id/${type.name}")
                }
            )
        }
        
        composable("library") {
            LibraryScreen(
                context = context,
                onItemClick = { id, type ->
                    navController.navigate("details/$id/${type.name}")
                }
            )
        }
        
        composable("profile") {
            ProfileScreen()
        }
        
        composable(
            "details/{movieId}/{mediaType}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.StringType },
                navArgument("mediaType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            val mediaType = when (backStackEntry.arguments?.getString("mediaType")) {
                "MOVIE" -> MediaType.MOVIE
                "TV" -> MediaType.TV
                else -> MediaType.MOVIE
            }
            
            DetailsScreen(
                movieId = movieId,
                mediaType = mediaType,
                onPlayClick = { /* Abrir player */ }
            )
        }
    }
}