package com.example.cinematch.ui.results

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinematch.databinding.FragmentResultsBinding
import com.example.cinematch.util.Result
import kotlinx.coroutines.launch

class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResultsViewModel by viewModels()
    private val args: ResultsFragmentArgs by navArgs()
    
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackHome.setOnClickListener {
            findNavController().popBackStack(com.example.cinematch.R.id.mainFragment, false)
        }

        setupRecyclerView()
        observeViewModel()

        // Fetch recommendations based on the genre passed from MainFragment
        binding.tvResultsTitle.text = "Recommendations for ${args.genre}"
        
        // Only fetch if we haven't already loaded data successfully to handle configuration changes better
        if (viewModel.uiState.value is Result.Loading || viewModel.uiState.value is Result.Error) {
             viewModel.getRecommendations(args.genre)
        }

        binding.fabRefresh.setOnClickListener {
            viewModel.refreshRecommendations(args.genre)
        }
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter { selectedMovie ->
            // Navigate to Details with the selected movie's ID
            val action = ResultsFragmentDirections.actionResultsFragmentToDetailsFragment(selectedMovie.imdbId)
            findNavController().navigate(action)
        }
        binding.rvMovies.adapter = adapter
        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvMovies.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rvMovies.visibility = View.VISIBLE
                            binding.tvError.visibility = View.GONE
                            adapter.submitList(state.data)
                            
                            if (state.data.isEmpty()) {
                                binding.tvError.text = "No results found."
                                binding.tvError.visibility = View.VISIBLE
                            }
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rvMovies.visibility = View.GONE
                            binding.tvError.visibility = View.VISIBLE
                            binding.tvError.text = state.exception.message ?: "An error occurred"
                            Toast.makeText(requireContext(), "Error loading movies", Toast.LENGTH_SHORT).show()
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
