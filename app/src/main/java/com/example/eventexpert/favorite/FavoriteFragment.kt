package com.example.eventexpert.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventexpert.R
import com.example.eventexpert.core.ui.EventAdapter
import com.example.eventexpert.core.ui.ViewModelFactory
import com.example.eventexpert.databinding.FragmentFavoriteBinding
import com.example.eventexpert.detail.DetailActivity

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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
            favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            favoriteViewModel.favoriteEvent.observe(viewLifecycleOwner) { dataEvent ->
                eventAdapter.submitList(dataEvent)
                binding.txtNoData.visibility =
                    if (dataEvent.isNotEmpty()) View.GONE else View.VISIBLE
            }

            with(binding.rvEvents) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = eventAdapter
            }
        }
    }

}