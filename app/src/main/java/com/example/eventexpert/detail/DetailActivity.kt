package com.example.eventexpert.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.eventexpert.R
import com.example.eventexpert.core.domain.model.Event
import com.example.eventexpert.core.ui.ViewModelFactory
import com.example.eventexpert.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailEventViewModel: DetailEventViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory.getInstance(this)
        detailEventViewModel = ViewModelProvider(this, factory)[DetailEventViewModel::class.java]

        val detailEvent = getParcelableExtra(intent, "DATA", Event::class.java)
        showDetailEvent(detailEvent)
    }

    private fun showDetailEvent(detailEvent: Event?) {
        detailEvent?.let {
            binding.apply {
                tvEventName.text = it.name
                tvEventCategoryCity.text = "${it.category} • ${it.cityName}"
                tvEventTime.text = "Start: ${it.beginTime} • End: ${it.endTime}"
                tvEventDescription.text = Html.fromHtml(it.description, Html.FROM_HTML_MODE_LEGACY)
                tvEventQuota.text = "Quota: ${it.quota} • Registrants: ${it.registrants}"

                Glide.with(this@DetailActivity)
                    .load(it.mediaCover)
                    .into(ivEventCover)

                btnOpenLink.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detailEvent.link))
                    startActivity(intent)
                }
            }

            var statusFavorite = it.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fabFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                detailEventViewModel.setFavoriteEvent(detailEvent, statusFavorite)
                setStatusFavorite(statusFavorite)
            }

        }
    }



    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.round_favorite_24))
        } else {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_border_24))
        }
    }
}