package com.superflix.app.ui.search

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.superflix.app.R
import com.superflix.app.data.api.SuperFlixApi
import com.superflix.app.data.models.Movie
import com.superflix.app.data.models.Series
import com.superflix.app.data.repositories.SearchRepository
import com.superflix.app.ui.details.DetailsActivity
import com.superflix.app.ui.home.HomeAdapter
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    
    private lateinit var searchView: SearchView
    private lateinit var resultsRecycler: RecyclerView
    private val api = SuperFlixApi()
    private lateinit var searchRepo: SearchRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        
        searchRepo = SearchRepository(api)
        
        searchView = findViewById(R.id.searchView)
        resultsRecycler = findViewById(R.id.resultsRecycler)
        resultsRecycler.layoutManager = GridLayoutManager(this, 2)
        
        val initialQuery = intent.getStringExtra("query") ?: ""
        if (initialQuery.isNotEmpty()) {
            searchView.setQuery(initialQuery, true)
        }
        
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                performSearch(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 2) performSearch(newText)
                return true
            }
        })
    }
    
    private fun performSearch(query: String) {
        lifecycleScope.launch {
            val results = searchRepo.search(query)
            val items = results.map { result ->
                when (result) {
                    is com.superflix.app.data.models.SearchResult.MovieResult -> result.movie
                    is com.superflix.app.data.models.SearchResult.SeriesResult -> result.series
                }
            }
            resultsRecycler.adapter = HomeAdapter(items) { item ->
                when (item) {
                    is Movie -> {
                        val intent = Intent(this@SearchActivity, DetailsActivity::class.java)
                        intent.putExtra("type", "filme")
                        intent.putExtra("id", item.id)
                        intent.putExtra("title", item.title)
                        startActivity(intent)
                    }
                    is Series -> {
                        val intent = Intent(this@SearchActivity, DetailsActivity::class.java)
                        intent.putExtra("type", "serie")
                        intent.putExtra("id", item.id)
                        intent.putExtra("title", item.title)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}