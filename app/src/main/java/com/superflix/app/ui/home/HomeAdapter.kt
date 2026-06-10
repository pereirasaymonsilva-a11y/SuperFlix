package com.superflix.app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.itemTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.itemImage)
        
        fun bind(title: String, poster: String, onClick: () -> Unit) {
            titleView.text = title
            
            Glide.with(itemView.context)
                .load(poster)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imageView)
            
            itemView.setOnClickListener { onClick() }
        }
    }
}