package com.example.cinematch.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinematch.databinding.FragmentWatchlistBinding
import kotlinx.coroutines.launch

class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchlistViewModel by viewModels()
    private lateinit var adapter: WatchlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackHome.setOnClickListener {
            findNavController().popBackStack(com.example.cinematch.R.id.mainFragment, false)
        }

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = WatchlistAdapter { imdbId ->
            // Navigate to Details via SafeArgs. We will update the nav_graph next.
            val action = WatchlistFragmentDirections.actionWatchlistFragmentToDetailsFragment(imdbId)
            findNavController().navigate(action)
        }
        binding.rvWatchlist.adapter = adapter
        binding.rvWatchlist.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedMovies.collect { movies ->
                    adapter.submitList(movies)
                    if (movies.isEmpty()) {
                        binding.tvEmptyWatchlist.visibility = View.VISIBLE
                        binding.rvWatchlist.visibility = View.GONE
                    } else {
                        binding.tvEmptyWatchlist.visibility = View.GONE
                        binding.rvWatchlist.visibility = View.VISIBLE
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
