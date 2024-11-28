package com.example.learnconnectapp.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.learnconnectapp.data.entity.Video

class VideoDiffCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean =
        oldItem.videoId == newItem.videoId

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
        oldItem == newItem
}