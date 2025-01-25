package com.example.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ui.EventAdapter
import com.example.eventexpert.detail.DetailActivity
import com.example.favorite.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadKoinModules(favoriteModule)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Event Favorite"
            setDisplayHomeAsUpEnabled(true)
        }

        val eventADapter = EventAdapter()
        eventADapter.onItemClick = { event ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("DATA", event)
            startActivity(intent)
        }

        favoriteViewModel.favoriteEvent.observe(this) { dataEvent ->
            eventADapter.submitList(dataEvent)
            binding.txtNoData.visibility =
                if (dataEvent.isNotEmpty()) View.GONE else View.VISIBLE
        }

        with(binding.rvEvents) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = eventADapter
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}