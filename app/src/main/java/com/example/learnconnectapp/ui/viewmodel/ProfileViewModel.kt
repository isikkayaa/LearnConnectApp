package com.example.learnconnectapp.ui.viewmodel

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.CurrentlyCourseList
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.room.CommentsDao
import com.example.learnconnectapp.room.KurslarDao
import com.example.learnconnectapp.room.VideoProgressDao
import com.google.firebase.auth.FirebaseAuth
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
        loadComments()
        loadCurrentlyCourses()
    }

    fun loadComments() {
        viewModelScope.launch {
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
