package com.example.learnconnectapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.data.entity.VideoProgress
import com.example.learnconnectapp.databinding.CardVideoBinding

class VideoAdapter(
    private var videos: List<Video>,
    private var videoProgress:  List<VideoProgress>,
    private val onVideoSelected: (Video) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(val binding: CardVideoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = CardVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        val progress = videoProgress.find { it.videoId == video.videoId }

        // Binding işlemleri
        holder.binding.videoNesnesi = video
        holder.binding.progressTextView.text = "Progress: ${progress?.progress ?: "No Progress"}"

        // Click listener
        holder.itemView.setOnClickListener {
            onVideoSelected(video) // Bu lambda doğru bir şekilde çalışmalı
        }
    }


    @SuppressLint("NotifyDataSetChanged")
        fun updateVideos(newVideos: List<Video>) { videos = newVideos
            notifyDataSetChanged()
        }

        @SuppressLint("NotifyDataSetChanged")
        fun updateVideoProgress(newProgress: List<VideoProgress>) {
            videoProgress = newProgress
            notifyDataSetChanged()
        }
    }
