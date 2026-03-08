package com.example.cinematch.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.cinematch.databinding.FragmentDetailsBinding
import com.example.cinematch.util.Result
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackHome.setOnClickListener {
            findNavController().popBackStack(com.example.cinematch.R.id.mainFragment, false)
        }

        observeViewModel()
        
        // Fetch movie details using the ID passed from ResultsFragment
        if (viewModel.uiState.value is Result.Loading || viewModel.uiState.value is Result.Error) {
             viewModel.getMovieDetails(args.imdbId)
        }

        setupSaveButton()
    }

    private fun setupSaveButton() {
        binding.btnSaveWatchlist.setOnClickListener {
            val state = viewModel.uiState.value
            if (state is Result.Success) {
                val rating = binding.ratingBar.rating
                viewModel.saveToWatchlist(state.data, rating) {
                    Toast.makeText(requireContext(), "Saved to Watchlist!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Movie details not fully loaded yet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is Result.Loading -> {
                            binding.detailProgressBar.visibility = View.VISIBLE
                            binding.tvDetailError.visibility = View.GONE
                        }
                        is Result.Success -> {
                            binding.detailProgressBar.visibility = View.GONE
                            binding.tvDetailError.visibility = View.GONE
                            
                            val movie = state.data
                            binding.tvDetailTitle.text = movie.title
                            binding.tvDetailInfo.text = "${movie.year} • ${movie.rated} • ${movie.runtime} • ${movie.genre}"
                            binding.tvDetailPlot.text = movie.plot
                            
                            // Load high-res poster if available
                            val posterUrl = movie.poster.replace("SX300", "SX1000") // Simple trick to get larger image from OMDb if available
                            binding.ivDetailPoster.load(posterUrl) {
                                crossfade(true)
                                fallback(android.R.drawable.ic_menu_gallery)
                            }
                        }
                        is Result.Error -> {
                            binding.detailProgressBar.visibility = View.GONE
                            binding.tvDetailError.visibility = View.VISIBLE
                            binding.tvDetailError.text = state.exception.message ?: "An error occurred"
                            Toast.makeText(requireContext(), "Error loading details", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
