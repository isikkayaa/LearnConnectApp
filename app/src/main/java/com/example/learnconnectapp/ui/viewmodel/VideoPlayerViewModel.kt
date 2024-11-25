package com.example.learnconnectapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.data.entity.VideoProgress
import com.example.learnconnectapp.room.VideoDao
import com.example.learnconnectapp.room.VideoProgressDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val videoProgressDao: VideoProgressDao,
    private val videoDao: VideoDao
) : ViewModel() {

    fun saveProgress(userId: Int, courseId: Int, videoId: Int, progress: Long) {
        viewModelScope.launch {
            val videoProgress = VideoProgress(userId = userId, courseId = courseId, videoId = videoId, progress = progress)
            videoProgressDao.insertProgress(videoProgress)
        }
    }

    fun getProgress(userId: Int, courseId: Int, videoId: Int, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val progress = videoProgressDao.getVideoProgressForCourse(courseId, userId).firstOrNull { it.videoId == videoId }?.progress ?: 0L
            onResult(progress)
        }
    }


    fun getVideosByCourseId(courseId: Int, onResult: (List<Video>) -> Unit) {
        viewModelScope.launch {
            val videos = videoDao.getVideosByCourseId(courseId)
            onResult(videos)
        }
    }
}

