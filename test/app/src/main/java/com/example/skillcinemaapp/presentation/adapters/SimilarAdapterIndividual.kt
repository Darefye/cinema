package com.example.skillcinemaapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinemaapp.data.remote.similar_movies.SimilarMovie
import com.example.skillcinemaapp.databinding.ItemFilmBinding


class SimilarAdapterIndividual(
    val onSimilarItemClick: (SimilarMovie) -> Unit
    ) : ListAdapter<SimilarMovie, SimilarViewHolderIndividual>(SimilarDiffUtilCallBack())
    {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolderIndividual {
            return SimilarViewHolderIndividual(
                binding = ItemFilmBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onSimilarItemClick = onSimilarItemClick
            )
        }

        override fun onBindViewHolder(holder: SimilarViewHolderIndividual, position: Int) {
            val item = getItem(position)
            holder.bind(item)
        }

    }

    class SimilarViewHolderIndividual(
        val binding: ItemFilmBinding,
        val onSimilarItemClick: (SimilarMovie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SimilarMovie) {
            with(binding) {

                Glide
                    .with(itemFilmPicture.context)
                    .load(item.posterUrl)
                    .centerCrop()
                    .into(itemFilmPicture)

                itemFilmGenre.text = ""
                itemFilmTitle.text = item.nameRu ?: item.nameEn ?: item.nameOriginal
                itemFilmRating.isVisible = false

                binding.root.setOnClickListener {
                    onSimilarItemClick(item)
                }
            }
        }
    }

