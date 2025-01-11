package com.example.eventexpert.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ui.EventAdapter
import com.example.eventexpert.databinding.FragmentFavoriteBinding
import com.example.eventexpert.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
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