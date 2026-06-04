// data/api/ApiService.kt
package com.superflix.app.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("lista")
    suspend fun getMovieIds(
        @Query("category") category: String = "filmes",
        @Query("type") type: String = "tmdb",
        @Query("order") order: String = "asc",
        @Query("limit") limit: Int = 50
    ): List<String>
    
    @GET("lista")
    suspend fun getSeriesIds(
        @Query("category") category: String = "series",
        @Query("type") type: String = "tmdb",
        @Query("order") order: String = "asc",
        @Query("limit") limit: Int = 50
    ): List<String>
    
    @GET("lista")
    suspend fun getAnimeIds(
        @Query("category") category: String = "animes",
        @Query("type") type: String = "tmdb",
        @Query("order") order: String = "asc",
        @Query("limit") limit: Int = 50
    ): List<String>
    
    @GET("lista")
    suspend fun search(
        @Query("category") category: String = "pesquisa",
        @Query("q") query: String
    ): List<String>
    
    @GET("lista")
    suspend fun getByGenre(
        @Query("category") category: String = "filme",
        @Query("genero") genre: String
    ): List<String>
}