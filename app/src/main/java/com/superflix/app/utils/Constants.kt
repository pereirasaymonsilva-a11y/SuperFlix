package com.superflix.app.utils

object Constants {
    const val BASE_API_URL = "https://superflixapi.fit"
    const val ENDPOINT_LISTA = "/lista"
    const val ENDPOINT_FILME = "/filme"
    
    private const val TMDB_IMAGE_BASE = "https://image.tmdb.org/t/p/w500"
    private const val TMDB_BACKDROP_BASE = "https://image.tmdb.org/t/p/w1280"
    
    fun getImageUrl(path: String?): String {
        return if (!path.isNullOrEmpty()) "$TMDB_IMAGE_BASE$path" else ""
    }
    
    fun getBackdropUrl(path: String?): String {
        return if (!path.isNullOrEmpty()) "$TMDB_BACKDROP_BASE$path" else ""
    }
}