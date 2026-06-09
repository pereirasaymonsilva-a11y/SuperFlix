package com.superflix.app.ui.details

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.superflix.app.R
import com.superflix.app.data.api.SuperFlixApi
import com.superflix.app.ui.player.PlayerActivity
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {
    
    private val api = SuperFlixApi()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        
        val type = intent.getStringExtra("type") ?: "filme"
        val id = intent.getStringExtra("id") ?: ""
        val title = intent.getStringExtra("title") ?: ""
        
        findViewById<TextView>(R.id.titleText).text = title
        
        val playButton = findViewById<Button>(R.id.playButton)
        playButton.setOnClickListener {
            val playerIntent = Intent(this, PlayerActivity::class.java)
            playerIntent.putExtra("type", type)
            playerIntent.putExtra("id", id)
            startActivity(playerIntent)
        }
        
        lifecycleScope.launch {
            loadDetails(type, id)
        }
    }
    
    private suspend fun loadDetails(type: String, id: String) {
        // Carrega detalhes adicionais se necessário
    }
}