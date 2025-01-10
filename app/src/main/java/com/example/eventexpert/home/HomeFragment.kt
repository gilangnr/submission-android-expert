package com.example.eventexpert.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventexpert.R
import com.example.eventexpert.core.data.Resource
import com.example.eventexpert.core.ui.EventAdapter
import com.example.eventexpert.core.ui.ViewModelFactory
import com.example.eventexpert.databinding.FragmentHomeBinding
import com.example.eventexpert.detail.DetailActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val eventAdapter = EventAdapter()
            eventAdapter.onItemClick = { event ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("DATA", event)
                startActivity(intent)
            }

            val factory = ViewModelFactory.getInstance(requireActivity())
            homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            homeViewModel.event.observe(viewLifecycleOwner) { event ->
                if (event != null) {
                    when(event) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            eventAdapter.submitList(event.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), "Ups ada kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            with(binding.rvEvents) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = eventAdapter
            }
        }
    }

}