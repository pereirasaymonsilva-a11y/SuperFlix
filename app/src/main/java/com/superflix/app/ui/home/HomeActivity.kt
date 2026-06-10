package com.superflix.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.superflix.app.R
import com.superflix.app.data.api.SuperFlixApi
import com.superflix.app.data.cache.LocalCache
import com.superflix.app.data.models.Movie
import com.superflix.app.data.models.Series
import com.superflix.app.data.repositories.MovieRepository
import com.superflix.app.data.repositories.SeriesRepository
import com.superflix.app.ui.details.DetailsActivity
import com.superflix.app.ui.search.SearchActivity  // <-- IMPORT ADICIONADO
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    
    private lateinit var moviesRecycler: RecyclerView
    private lateinit var seriesRecycler: RecyclerView
    private lateinit var animesRecycler: RecyclerView
    private lateinit var searchView: SearchView
    
    private val api = SuperFlixApi()
    private lateinit var cache: LocalCache
    private lateinit var movieRepo: MovieRepository
    private lateinit var seriesRepo: SeriesRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        
        cache = LocalCache(this)
        movieRepo = MovieRepository(api, cache)
        seriesRepo = SeriesRepository(api, cache)
        
        setupViews()
        loadData()
    }
    
    private fun setupViews() {
        moviesRecycler = findViewById(R.id.moviesRecycler)
        seriesRecycler = findViewById(R.id.seriesRecycler)
        animesRecycler = findViewById(R.id.animesRecycler)
        searchView = findViewById(R.id.searchView)
        
        moviesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        seriesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        animesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val intent = Intent(this@HomeActivity, SearchActivity::class.java)
                intent.putExtra("query", query)
                startActivity(intent)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean = false
        })
    }
    
    private fun loadData() {
        lifecycleScope.launch {
            val movies = movieRepo.getMovies("filme")
            val series = seriesRepo.getSeries("serie")
            val animes = seriesRepo.getSeries("anime")
            
            moviesRecycler.adapter = HomeAdapter(movies) { movie ->
                openDetails("filme", movie.id, movie.title)
            }
            
            seriesRecycler.adapter = HomeAdapter(series) { serie ->
                openDetails("serie", serie.id, serie.title)
            }
            
            animesRecycler.adapter = HomeAdapter(animes) { anime ->
                openDetails("anime", anime.id, anime.title)
            }
        }
    }
    
    private fun openDetails(type: String, id: String, title: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("id", id)
        intent.putExtra("title", title)
        startActivity(intent)
    }
}