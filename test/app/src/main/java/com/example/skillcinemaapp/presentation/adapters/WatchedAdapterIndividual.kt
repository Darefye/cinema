package com.example.skillcinemaapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinemaapp.data.local.entities.Movie
import com.example.skillcinemaapp.databinding.MovieItemSelectionBinding



class WatchedAdapterIndividual(
    val onWatchedItemClick: (Movie) -> Unit
) :
    ListAdapter<Movie, WatchedHolderIndividual>(DiffUtilCallBackWatched()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchedHolderIndividual {
        return WatchedHolderIndividual(
            binding = MovieItemSelectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onWatchedItemClick = onWatchedItemClick
        )
    }

    override fun onBindViewHolder(holder: WatchedHolderIndividual, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}

class WatchedHolderIndividual(
    val binding: MovieItemSelectionBinding,
    val onWatchedItemClick: (Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        with(binding) {
            Glide
                .with(filmPicture.context)
                .load(item.posterUri)
                .centerCrop()
                .into(filmPicture)

            movieGenre.text = item.genre
            filmTitle.text = item.movieName
            filmRating.text = item.rating.toString()
        }
        binding.root.setOnClickListener {
            onWatchedItemClick(item)
        }
    }
}



