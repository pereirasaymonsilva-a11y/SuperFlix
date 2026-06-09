// utils/Constants.kt
package com.superflix.app.utils

object Constants {
    // API
    const val BASE_API_URL = "https://superflixapi.fit"
    
    // Imagens TMDB
    private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    const val POSTER_SIZE = "w500"
    const val BACKDROP_SIZE = "w1280"
    
    // Endpoints
    const val ENDPOINT_LISTA = "/lista"
    const val ENDPOINT_FILME = "/filme"
    const val ENDPOINT_SERIE = "/serie"
    
    fun getImageUrl(path: String?): String {
        return if (!path.isNullOrEmpty()) {
            "$TMDB_IMAGE_BASE_URL$POSTER_SIZE$path"
        } else {
            "" // URL de placeholder
        }
    }
    
    fun getBackdropUrl(path: String?): String {
        return if (!path.isNullOrEmpty()) {
            "$TMDB_IMAGE_BASE_URL$BACKDROP_SIZE$path"
        } else {
            ""
        }
    }
}