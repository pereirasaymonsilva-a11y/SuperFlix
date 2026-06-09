package com.superflix.app.data.models

sealed class SearchResult {
    data class MovieResult(val movie: Movie) : SearchResult()
    data class SeriesResult(val series: Series) : SearchResult()
}