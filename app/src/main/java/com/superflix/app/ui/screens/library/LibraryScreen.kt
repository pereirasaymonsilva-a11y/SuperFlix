package com.superflix.app.ui.screens.library

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superflix.app.data.database.FavoriteEntity
import com.superflix.app.data.database.HistoryEntity
import com.superflix.app.data.models.MediaType
import com.superflix.app.ui.components.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    context: Context,
    onItemClick: (String, MediaType) -> Unit,
    viewModel: LibraryViewModel = viewModel()  // SEM Factory
) {
    var selectedTab by remember { mutableStateOf(0) }
    val favorites by viewModel.favorites.collectAsState()
    val history by viewModel.history.collectAsState()
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Minha Biblioteca") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                listOf("Favoritos", "Histórico").forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            
            when (selectedTab) {
                0 -> {
                    if (favorites.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Nenhum favorito ainda")
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(favorites) { favorite ->
                                MovieCard(
                                    movieId = favorite.id,
                                    title = favorite.title,
                                    posterPath = favorite.posterPath,
                                    onClick = { onItemClick(favorite.id, favorite.type) }
                                )
                            }
                        }
                    }
                }
                1 -> {
                    if (history.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Nenhum histórico encontrado")
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(history) { historyItem ->
                                MovieCard(
                                    movieId = historyItem.id,
                                    title = historyItem.title,
                                    posterPath = historyItem.posterPath,
                                    onClick = { onItemClick(historyItem.id, historyItem.type) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}