package com.example.skillcinemaapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinemaapp.data.remote.custom_selection_dto.ItemCustom
import com.example.skillcinemaapp.databinding.MovieItemSelectionBinding


open class FirstCustomAdapterIndividual(
    val onFirstCustomItemClick: (ItemCustom) -> Unit
) : PagingDataAdapter<ItemCustom, CustomViewHolderIndividual>(DiffUtilCallBackCustom()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderIndividual {
        return CustomViewHolderIndividual(
            binding = MovieItemSelectionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onFirstCustomItemClick = onFirstCustomItemClick
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolderIndividual, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}

class CustomViewHolderIndividual(
    val binding: MovieItemSelectionBinding, val onFirstCustomItemClick: (ItemCustom) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemCustom) {
        with(binding) {
            Glide.with(filmPicture.context).load(item.posterUrlPreview).centerCrop()
                .into(filmPicture)

            movieGenre.text = item.genres.firstOrNull()?.genre

            if (item.watchedStatus != null) {
                watchedStatus.isVisible = item.watchedStatus == true
            } else watchedStatus.isVisible = false

            if (item.nameRu == null && item.nameEn == null && item.nameOriginal == null) {
                filmTitle.isVisible = false
            } else {
                filmTitle.text = when {
                    item.nameRu != null -> item.nameRu
                    item.nameEn != null -> item.nameEn.toString()
                    else -> item.nameOriginal
                }
            }

            if (item.ratingKinopoisk == null && item.ratingImdb == null) {
                filmRating.isVisible = false
            } else {
                filmRating.text = when {
                    item.ratingKinopoisk != null -> item.ratingKinopoisk.toString()
                    else -> item.ratingImdb.toString()
                }
            }
            binding.root.setOnClickListener {
                onFirstCustomItemClick(item)
            }
        }
    }
}


