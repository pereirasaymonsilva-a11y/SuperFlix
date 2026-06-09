package com.superflix.app.ui.screens.library

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superflix.app.data.models.MediaType
import com.superflix.app.ui.components.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    context: Context,
    onItemClick: (String, MediaType) -> Unit,
    viewModel: LibraryViewModel = viewModel(factory = LibraryViewModelFactory(context))
) {
    var selectedTab by remember { mutableStateOf(0) }
    val favorites = viewModel.favorites
    val history = viewModel.history
    val tabs = listOf("Favoritos", "Histórico")
    
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
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            
            val items = if (selectedTab == 0) favorites else history
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    MovieCard(
                        movieId = item.id,
                        onClick = { onItemClick(item.id, item.type) }
                    )
                }
            }
        }
    }
}