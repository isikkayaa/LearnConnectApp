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

    private val _downloadedVideos = MutableLiveData<List<DownloadKurslar>>()
    val downloadedVideos: LiveData<List<DownloadKurslar>> get() = _downloadedVideos

    init {
        loadDownloadedVideos()
    }

    fun loadDownloadedVideos() {
        viewModelScope.launch {
            val downloads = downloadDao.getAllDownloads()
            _downloadedVideos.postValue(downloads)
        }
    }
}
