package com.example.learnconnectapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnectapp.data.entity.DownloadKurslar
import com.example.learnconnectapp.room.DownloadDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val downloadDao: DownloadDao
) : ViewModel() {
    private val _downloadedCourses = MutableLiveData<List<DownloadKurslar>>()
    val downloadedCourses: LiveData<List<DownloadKurslar>> get() = _downloadedCourses

    init {
        loadDownloadedCourses()
    }

    fun loadDownloadedCourses() {
        viewModelScope.launch {
            val downloads = downloadDao.getAllDownloads()
            _downloadedCourses.postValue(downloads)
        }
    }

    fun saveDownloadedCourse(downloadKurs: DownloadKurslar) {
        viewModelScope.launch {
            val existingDownload = downloadDao.getDownloadByCourseId(downloadKurs.download_kurs_id)
            if (existingDownload == null) {
                downloadDao.insert(downloadKurs)
                loadDownloadedCourses() // Refresh the list
            }
        }
    }

    fun deleteDownload(downloadKurs: DownloadKurslar) {
        viewModelScope.launch {
            downloadDao.delete(downloadKurs)
            loadDownloadedCourses()
        }
    }
}