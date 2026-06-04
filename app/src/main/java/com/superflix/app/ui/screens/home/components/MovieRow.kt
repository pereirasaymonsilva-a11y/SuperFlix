// ui/screens/home/components/MovieRow.kt
package com.superflix.app.ui.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.superflix.app.data.models.MediaType
import com.superflix.app.ui.components.MovieCard

@Composable
fun MovieRow(
    movieIds: List<String>,
    title: String,
    onMovieClick: (String, MediaType) -> Unit
) {
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