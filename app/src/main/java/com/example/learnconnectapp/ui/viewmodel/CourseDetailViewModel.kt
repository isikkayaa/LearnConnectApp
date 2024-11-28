package com.example.learnconnectapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.CurrentlyCourseList
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.data.entity.VideoProgress
import com.example.learnconnectapp.room.CommentsDao
import com.example.learnconnectapp.room.CurrentlyCourseDao
import com.example.learnconnectapp.room.VideoDao
import com.example.learnconnectapp.room.VideoProgressDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val videoDao: VideoDao,
    private val commentsDao: CommentsDao,
    private val videoProgressDao: VideoProgressDao,
    private val currentlyCourseDao: CurrentlyCourseDao
) : ViewModel() {

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> get() = _videos

    private val _comments = MutableLiveData<List<Comments>>()
    val comments: LiveData<List<Comments>> get() = _comments



    private val _videoProgress = MutableLiveData<List<VideoProgress>>()
    val videoProgress : LiveData<List<VideoProgress>> get() = _videoProgress

    fun loadVideos(courseId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                val videoList = videoDao.getVideosByCourseId(courseId)
                Log.d("CourseDetailViewModel", "Loaded videos for course $courseId: $videoList")

                if (videoList.isNullOrEmpty()) {
                    val dummyVideos = listOf(
                        Video(1, courseId, 1, "Introduction to Kotlin", "https://example.com/kotlin_intro.mp4"),
                        Video(2, courseId, 2, "Advanced Kotlin", "https://example.com/kotlin_advanced.mp4")
                    )
                    _videos.postValue(dummyVideos)
                } else {
                    _videos.postValue(videoList)
                }

                val progressList = videoProgressDao.getVideoProgressForCourse(courseId, userId)
                _videoProgress.postValue(progressList)
            } catch (e: Exception) {
                Log.e("CourseDetailViewModel", "Error loading videos", e)
                _videos.postValue(emptyList())
            }
        }
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

    fun insertCurrentlyCourse(course: CurrentlyCourseList) {
        viewModelScope.launch {
            currentlyCourseDao.insertCurrentlyCourse(course)
        }
    }

    fun deleteCurrentlyCourse(courseId: Int){
        viewModelScope.launch{
            currentlyCourseDao.deleteCurrentlyCourse(courseId)
        }
    }
    fun getCurrentlyCourse(courseId: Int) {
        viewModelScope.launch {
            currentlyCourseDao.getCurrentlyCourse(courseId)
        }
    }


    fun addComment(comment: Comments) {
        viewModelScope.launch {
            commentsDao.insert(comment)
            val updatedComments = commentsDao.getCommentsByCourseTitle(comment.courseTitle)
            _comments.postValue(updatedComments)
        }
    }
}
