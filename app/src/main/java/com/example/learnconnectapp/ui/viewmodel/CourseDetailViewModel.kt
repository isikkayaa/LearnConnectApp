package com.example.learnconnectapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.data.entity.VideoProgress
import com.example.learnconnectapp.room.CommentsDao
import com.example.learnconnectapp.room.VideoDao
import com.example.learnconnectapp.room.VideoProgressDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val videoDao: VideoDao,
    private val commentsDao: CommentsDao,
    private val videoProgressDao: VideoProgressDao
) : ViewModel() {

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> get() = _videos


    private val _videoProgress = MutableLiveData<List<VideoProgress>>()
    val videoProgress : LiveData<List<VideoProgress>> get() = _videoProgress

    fun loadVideos(courseId: Int, userId: Int) {
        viewModelScope.launch {
            val videoList = videoDao.getVideosByCourseId(courseId)
            _videos.postValue(videoList)

            val progressList = videoProgressDao.getVideoProgressForCourse(courseId, userId)
            _videoProgress.postValue(progressList) }
    }


    fun loadVideoProgress(courseId: Int, userId: Int) {
        viewModelScope.launch {
            val progressList = videoProgressDao.getVideoProgressForCourse(courseId, userId)
            _videoProgress.postValue(progressList) }}

    fun getVideoUrl(courseId: Int, videoId: Int, onResult: (String) -> Unit) {
        viewModelScope.launch {
            val videoUrl = videoDao.getVideoUrl(courseId, videoId)
            onResult(videoUrl)
        }
    }

    fun addComments(comment: Comments) {
        viewModelScope.launch {
            commentsDao.insert(comment) // `CommentsDao` ile uyuşmalı
        }
    }
}
