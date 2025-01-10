package com.example.eventexpert.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventexpert.core.domain.model.Event
import com.example.eventexpert.databinding.ItemEventBinding

class EventAdapter : ListAdapter<Event, EventAdapter.EventViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventViewHolder(
            ItemEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class EventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Event) {
            Glide.with(itemView.context)
                .load(data.mediaCover)
                .into(binding.ivItemImage)
            binding.tvItemTitle.text = data.name

            itemView.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.eventId == newItem.eventId
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }
        }
    }
}
