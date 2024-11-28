package com.example.learnconnectapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.CurrentlyCourseList
import com.example.learnconnectapp.room.CommentsDao
import com.example.learnconnectapp.room.KurslarDao
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val commentsDao: CommentsDao,
    private val kurslarDao: KurslarDao
) : ViewModel() {
    val userName = MutableLiveData<String>()

    private val _comments = MutableLiveData<List<Comments>>()
    val comments: LiveData<List<Comments>> get() = _comments

    private val _currentlyCourses = MutableLiveData<List<CurrentlyCourseList>>()
    val currentlyCourses: LiveData<List<CurrentlyCourseList>> get() = _currentlyCourses

    private val sharedPreferences = context.getSharedPreferences("LearnConnectApp", Context.MODE_PRIVATE)

    init {
        loadUserComments()

        loadCurrentlyCourses()
    }

    fun loadCurrentlyCoursesFromPreferences() {
        viewModelScope.launch {
            val courseList = mutableListOf<CurrentlyCourseList>()
            val allEntries = sharedPreferences.all

            for ((key, value) in allEntries) {
                if (key.startsWith("enrollment_status_") && value == true) {
                    val courseId = key.removePrefix("enrollment_status_").toInt()
                    val course = kurslarDao.getCourseById(courseId)
                    if (course != null) {
                        courseList.add(
                            CurrentlyCourseList(
                                currently_kurs_id = course.kurs_id,
                                currently_kurs_isim = course.kurs_isim,
                                imageLinks = course.kurs_gorsel
                            )
                        )
                    }
                }
            }
            _currentlyCourses.postValue(courseList)
        }
    }


    fun loadComments() {
        viewModelScope.launch {
            val commentsList = mutableListOf<Comments>()
            val allEntries = sharedPreferences.all

            for ((key, value) in allEntries) {
                if (key.startsWith("enrollment_status_") && value == true) {
                    val courseId = key.removePrefix("enrollment_status_").toInt()
                    val comments = commentsDao.getCommentsByUser(courseId)
                    if (comments != null) {
                        commentsList.addAll(comments.map { comment ->
                            Comments(
                                comment_id = comment.comment_id,
                                courseTitle = comment.courseTitle,
                                userComment = comment.userComment,
                                courseImageUrl = comment.courseImageUrl,
                                rating = comment.rating
                            )
                        })
                    }
                }
            }
            _comments.postValue(commentsList)
        }
    }



    fun loadUserComments(){
        viewModelScope.launch{
            val commentsList = commentsDao.getCommentsByUser(1)
            _comments.postValue(commentsList)
        }
    }

    fun loadCurrentlyCourses() {
        viewModelScope.launch {
            val courseList = kurslarDao.getCurrentlyCoursesByUser(1)
            _currentlyCourses.postValue(courseList)
        }
    }
}
