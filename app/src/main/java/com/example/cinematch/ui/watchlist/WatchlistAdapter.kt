package com.example.cinematch.ui.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinematch.data.db.MovieEntity
import com.example.cinematch.databinding.ItemWatchlistMovieBinding

class WatchlistAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<MovieEntity, WatchlistAdapter.WatchlistViewHolder>(WatchlistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val binding = ItemWatchlistMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchlistViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WatchlistViewHolder(
        private val binding: ItemWatchlistMovieBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntity) {
            binding.tvWatchlistTitle.text = movie.title
            binding.tvWatchlistGenre.text = movie.genre
            binding.ratingBarWatchlist.rating = movie.userRating
            
            // Load image using Coil
            binding.ivWatchlistPoster.load(movie.posterUrl) {
                crossfade(true)
            }

            binding.root.setOnClickListener {
                onClick(movie.imdbId)
            }
        }
    }

    class WatchlistDiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.imdbId == newItem.imdbId
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }
}
