package com.superflix.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.superflix.app.R
import com.superflix.app.data.models.Movie
import com.superflix.app.data.models.Series

class HomeAdapter(
    private val items: List<Any>,
    private val onItemClick: (Any) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val title = when (item) {
            is Movie -> item.title
            is Series -> item.title
            else -> ""
        }
        val poster = when (item) {
            is Movie -> item.posterPath
            is Series -> item.posterPath
            else -> ""
        }
        
        holder.bind(title, poster) { onItemClick(item) }
    }
    
    override fun getItemCount() = items.size
    
    class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String, poster: String, onClick: () -> Unit) {
            val titleView = itemView.findViewById<android.widget.TextView>(R.id.itemTitle)
            val imageView = itemView.findViewById<android.widget.ImageView>(R.id.itemImage)
            
            titleView.text = title
            
            Glide.with(itemView.context)
                .load(poster)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imageView)
            
            itemView.setOnClickListener { onClick() }
        }
    }
}