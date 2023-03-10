package com.example.skillcinemaapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinemaapp.data.local.entities.Movie
import com.example.skillcinemaapp.databinding.ClearHistoryItemBinding
import com.example.skillcinemaapp.databinding.ItemFilmBinding


open class InterestingMoviesAdapterCommon(
    val onInterestingItemClick: (Movie) -> Unit, val onClearInterestingClick: (View) -> Unit
) : ListAdapter<Movie, RecyclerView.ViewHolder>(DiffUtilCallBackWatched()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_MOVIES) {
            InterestingViewHolderCommon(
                binding = ItemFilmBinding.inflate(
                    layoutInflater, parent, false
                ), onInterestingItemClick = onInterestingItemClick
            )
        } else ClearInterestingViewHolder(
            binding = ClearHistoryItemBinding.inflate(
                layoutInflater, parent, false
            ), onClearInterestingClick = onClearInterestingClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            if (getItemViewType(position) == ITEM_MOVIES) {
                (holder as InterestingViewHolderCommon).bind(it)
            } else (holder as ClearInterestingViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 10) ITEM_CLEAR_HISTORY
        else ITEM_MOVIES
    }

    companion object {
        private const val ITEM_MOVIES = 0
        private const val ITEM_CLEAR_HISTORY = 1
    }
}

class ClearInterestingViewHolder(
    val binding: ClearHistoryItemBinding, val onClearInterestingClick: (View) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        with(binding) {
            trashPicture.setOnClickListener {
                onClearInterestingClick(it)
            }
        }
    }
}

class InterestingViewHolderCommon(
    val binding: ItemFilmBinding, val onInterestingItemClick: (Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        with(binding) {
            Glide.with(itemFilmPicture.context).load(item.posterUri).centerCrop()
                .into(itemFilmPicture)

            itemFilmGenre.text = item.genre ?: ""

            itemFilmTitle.text = item.movieName

            itemFilmRating.text = item.rating.toString()
        }

        binding.root.setOnClickListener {
            onInterestingItemClick(item)
        }
    }
}


