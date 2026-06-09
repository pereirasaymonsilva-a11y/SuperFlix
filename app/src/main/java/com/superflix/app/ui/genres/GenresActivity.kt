package com.superflix.app.ui.genres

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.superflix.app.R
import com.superflix.app.data.api.SuperFlixApi

class GenresActivity : AppCompatActivity() {
    
    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private val api = SuperFlixApi()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)
        
        spinner = findViewById(R.id.categorySpinner)
        recyclerView = findViewById(R.id.genresRecycler)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        
        val categories = arrayOf("Filmes", "Séries", "Animes")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinner.adapter = adapter
        
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                loadGenres(categories[position].lowercase())
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
    
    private fun loadGenres(category: String) {
        // TODO: Load genres from API
    }
}