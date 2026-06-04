// ui/screens/home/HomeScreen.kt
package com.superflix.app.ui.screens.home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superflix.app.data.models.MediaType
import com.superflix.app.ui.components.BottomNavigationBar
import com.superflix.app.ui.screens.home.components.BannerCard
import com.superflix.app.ui.screens.home.components.MovieRow
import com.superflix.app.ui.screens.home.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    context: Context,
    onMovieClick: (String, MediaType) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(context))
) {
    val movieIds by viewModel.movieIds.collectAsState()
    val seriesIds by viewModel.seriesIds.collectAsState()
    val animeIds by viewModel.animeIds.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "SuperFlix",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // Banner
                if (movieIds.isNotEmpty()) {
                    item {
                        BannerCard(
                            movieId = movieIds.firstOrNull() ?: "",
                            onClick = { onMovieClick(movieIds.first(), MediaType.MOVIE) }
                        )
                    }
                }
                
                // Filmes Populares
                if (movieIds.isNotEmpty()) {
                    item { SectionHeader(title = "🔥 Filmes Populares") }
                    item {
                        MovieRow(
                            movieIds = movieIds.take(10),
                            title = "Populares",
                            onMovieClick = onMovieClick
                        )
                    }
                }
                
                // Séries
                if (seriesIds.isNotEmpty()) {
                    item { SectionHeader(title = "📺 Séries em Alta") }
                    item {
                        MovieRow(
                            movieIds = seriesIds.take(10),
                            title = "Séries",
                            onMovieClick = { id, _ -> onMovieClick(id, MediaType.SERIES) }
                        )
                    }
                }
                
                // Animes
                if (animeIds.isNotEmpty()) {
                    item { SectionHeader(title = "🎌 Animes Populares") }
                    item {
                        MovieRow(
                            movieIds = animeIds.take(10),
                            title = "Animes",
                            onMovieClick = { id, _ -> onMovieClick(id, MediaType.ANIME) }
                        )
                    }
                }
                
                // Recomendados
                if (movieIds.size > 10) {
                    item { SectionHeader(title = "🎬 Recomendados para Você") }
                    item {
                        MovieRow(
                            movieIds = movieIds.drop(10).take(10),
                            title = "Recomendados",
                            onMovieClick = onMovieClick
                        )
                    }
                }
            }
        }
    }
}