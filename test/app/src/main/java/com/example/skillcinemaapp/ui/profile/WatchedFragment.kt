package com.example.skillcinemaapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.data.local.entities.Movie
import com.example.skillcinemaapp.databinding.FragmentWatchedBinding
import com.example.skillcinemaapp.presentation.MovieViewModel
import com.example.skillcinemaapp.presentation.adapters.WatchedAdapterIndividual
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class WatchedFragment : Fragment() {

    private var _binding: FragmentWatchedBinding? = null
    private val binding get() = _binding!!

    private lateinit var backButton: AppCompatImageButton

    private lateinit var watchedRecycler: RecyclerView

    private val watchedAdapter = WatchedAdapterIndividual(
        onWatchedItemClick = { movie -> onItemClickWatched(movie) }
    )


    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton = binding.backButton

        watchedRecycler = binding.watchedRecyclerView
        watchedRecycler.adapter = watchedAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            movieViewModel.watchedList.collectLatest {
                if (it.isNotEmpty()) {
                    watchedRecycler.isVisible = true
                    watchedAdapter.submitList(it)
                } else watchedRecycler.isVisible = false
            }
        }

        backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun onItemClickWatched(movie: Movie){
        movieViewModel.movieSelected(movie.movieId)
        movieViewModel.getImagesList(movie.movieId)
        movieViewModel.getStaffInfo(movie.movieId)
        movieViewModel.getActorsInfo(movie.movieId)
        movieViewModel.getSimilarMovies(movie.movieId)
        movieViewModel.getSeriesInfo(movie.movieId)
        movieViewModel.getMovieFromDataBaseById(movie.movieId)
        findNavController().navigate(R.id.action_watchedFragment_to_movieDetailsFragment)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}