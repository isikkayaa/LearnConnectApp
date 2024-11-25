package com.example.learnconnectapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.data.repository.KurslarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val kurslarRepository: KurslarRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _kurslarListesi = MutableLiveData<List<Kurslar>?>()
    val kurslarListesi: LiveData<List<Kurslar>?> get() = _kurslarListesi

    private val _searchResults = MutableLiveData<List<Kurslar>?>()
    val searchResults: LiveData<List<Kurslar>?> get() = _searchResults

    private val _favKurslar = MutableLiveData<List<FavoriKurslar>>()
    val favKurslar: LiveData<List<FavoriKurslar>> get() = _favKurslar

    init {
        kurslariYukle(1)
    }

    fun kurslariYukle(kursId: Int) {
        viewModelScope.launch {
            val kurslar = kurslarRepository.kurslariYukle(kursId)
            _kurslarListesi.postValue(kurslar)
        }
    }

    fun searchKurslar(query: String) {
        viewModelScope.launch {
            val results = kurslarRepository.searchCourses(query)
            _searchResults.postValue(results)
        }
    }
}
