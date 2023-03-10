package com.example.skillcinemaapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.skillcinemaapp.data.remote.premiers_dto.Item
import com.example.skillcinemaapp.databinding.ItemFilmBinding
import com.example.skillcinemaapp.databinding.ShowAllItemBinding


class PremiersAdapterCommon(
    val onPremiersItemClick: (Item) -> Unit,
    val onShowAllPremiersClick: (View) -> Unit
) : ListAdapter<Item, ViewHolder>(PremiersDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_MOVIES) {
            PremiersViewHolderCommon(
                binding = ItemFilmBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ),
                onItemClick = onPremiersItemClick
            )
        } else ShowAllViewHolder(
            binding = ShowAllItemBinding.inflate(
                layoutInflater,
                parent,
                false
            ),
            onShowAllItemClick = onShowAllPremiersClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            if (getItemViewType(position) == ITEM_MOVIES) {
                (holder as PremiersViewHolderCommon).bind(it)
            } else (holder as ShowAllViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 20) ITEM_SHOW_ALL
        else ITEM_MOVIES
    }

    companion object {
        private const val ITEM_MOVIES = 0
        private const val ITEM_SHOW_ALL = 1
    }
}

class PremiersViewHolderCommon(
    val binding: ItemFilmBinding,
    val onItemClick: (Item) -> Unit
) : ViewHolder(binding.root) {
    fun bind(item: Item) {
        with(binding) {

            itemFilmPicture.isVisible = true
            itemFilmGenre.isVisible = true
            itemFilmTitle.isVisible = true
            itemFilmRating.isVisible = false

            Glide
                .with(itemFilmPicture.context)
                .load(item.posterUrl)
                .centerCrop()
                .into(itemFilmPicture)
            if(item.watched_status !=null){
               itemFilmWatchedStatus.isVisible = item.watched_status == true
            } else itemFilmWatchedStatus.isVisible = false

            itemFilmGenre.text = item.genres.firstOrNull()?.genre
            itemFilmTitle.text = item.nameRu

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}


class PremiersDiffUtilCallBack : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.kinopoiskId == newItem.kinopoiskId
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}

