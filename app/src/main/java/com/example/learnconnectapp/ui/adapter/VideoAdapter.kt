package com.example.learnconnectapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.data.entity.VideoProgress
import com.example.learnconnectapp.databinding.CardVideoBinding
import com.example.learnconnectapp.ui.viewmodel.CourseDetailViewModel

class VideoAdapter(
    private val courseDetailViewModel: CourseDetailViewModel,
    private val onVideoSelected: (Video) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var videoList: List<Video> = emptyList()
    private var videoProgress: Map<Int, Long> = emptyMap()

    fun submitList(videos: List<Video>?) {
        val newList = videos.orEmpty()
        if (newList != videoList) {
            videoList = newList
            notifyDataSetChanged()
        }
    }
    fun updateProgress(progressList: List<VideoProgress>) {
        videoProgress = progressList.associateBy({ it.videoId }, { it.progress })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardVideoBinding.inflate(inflater, parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]
        holder.bind(video, videoProgress[video.videoId] ?: 0, onVideoSelected)
    }

    override fun getItemCount(): Int = videoList.size

    inner class VideoViewHolder(private val binding: CardVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: Video, progress: Long, onVideoSelected: (Video) -> Unit) {
            binding.videoNesnesi = video
            binding.textViewvideoAdi.text = video.videoTitle
            binding.progressTextView.text = "Progress: $progress"
            binding.cardViewVideo.setOnClickListener { onVideoSelected(video) }
        }
    }
}

