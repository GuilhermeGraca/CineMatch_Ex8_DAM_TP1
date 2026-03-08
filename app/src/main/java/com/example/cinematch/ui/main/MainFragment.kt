package com.example.cinematch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinematch.R
import com.example.cinematch.databinding.FragmentMainBinding
import com.google.android.material.chip.Chip

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by viewModels()

    // Since OMDb doesn't specifically support literal genre queries perfectly,
    // we use keywords that work well as search terms.
    private val genres = listOf("Action", "Comedy", "Drama", "Sci-Fi", "Horror", "Romance", "Thriller", "Animation", "Documentary")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGenreChips()
        setupSearchBox()

        binding.btnGetRecommendations.setOnClickListener {
            val checkedChipId = binding.chipGroupGenres.checkedChipId
            if (checkedChipId != View.NO_ID) {
                val chip = binding.chipGroupGenres.findViewById<Chip>(checkedChipId)
                val selectedGenre = chip.text.toString()
                
                // Navigate to Results Fragment with selected genre
                val action = MainFragmentDirections.actionMainFragmentToResultsFragment(selectedGenre)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please select a genre", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnViewWatchlist.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_watchlistFragment)
        }
    }

    private fun setupSearchBox() {
        binding.tilSearch.setEndIconOnClickListener {
            performSearch()
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    private fun performSearch() {
        val query = binding.etSearch.text.toString().trim()
        if (query.isNotEmpty()) {
            val action = MainFragmentDirections.actionMainFragmentToResultsFragment(query)
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please enter a movie title", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupGenreChips() {
        for (genre in genres) {
            val chip = Chip(requireContext()).apply {
                text = genre
                isCheckable = true
                isClickable = true
                // Optional styling could be applied here
            }
            binding.chipGroupGenres.addView(chip)
        }
        
        // Select the first chip by default if there are any
        if (binding.chipGroupGenres.childCount > 0) {
            (binding.chipGroupGenres.getChildAt(0) as Chip).isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
