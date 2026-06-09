package com.superflix.app.ui.screens.home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superflix.app.data.models.MediaType
import com.superflix.app.ui.components.BottomNavigationBar
import com.superflix.app.ui.components.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    context: Context,
    onMovieClick: (String, MediaType) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(context))
) {
    val movieIds = viewModel.movieIds.value
    val isLoading = viewModel.isLoading.value
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("SuperFlix") }
            )
        },
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    Text(
                        text = "Filmes Populares",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(movieIds) { id ->
                            MovieCard(
                                movieId = id,
                                onClick = { onMovieClick(id, MediaType.MOVIE) }
                            )
                        }
                    }
                }
            }
        }
    }
}