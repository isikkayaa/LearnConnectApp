package com.example.learnconnectapp.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.FavoriKurslarSimple
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.data.repository.KurslarRepository
import com.example.learnconnectapp.room.KisilerDao
import com.example.learnconnectapp.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val kurslarRepository: KurslarRepository,
    private val kisilerDao: KisilerDao,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val sharedPreferences = SharedPreferences(context)
    private val _favoriteCourses = MutableLiveData<List<FavoriKurslar>>(sharedPreferences.getFavorites())
    val favoriteCourses: LiveData<List<FavoriKurslar>> get() = _favoriteCourses

    private val _kurslarListesi = MutableLiveData<List<Kurslar>?>()
    val kurslarListesi: LiveData<List<Kurslar>?> get() = _kurslarListesi

    private val _searchResults = MutableLiveData<List<Kurslar>?>()
    val searchResults: LiveData<List<Kurslar>?> get() = _searchResults


    init {
        kurslariYukle()
    }


    fun kurslariYukle() {
        val dummyCourses = listOf(
            Kurslar(1, "Android Development", R.drawable.android_category_thumbnail),
            Kurslar(2, "Kotlin for Beginners", R.drawable.kotlin),
            Kurslar(3, "Data Structures", R.drawable.data),
            Kurslar(4, "Artificial Intelligence", R.drawable.yapay),
            Kurslar(5, "Basic Jetpack Compose", R.drawable.jetpack)

        )
        _kurslarListesi.postValue(dummyCourses) }

    fun searchKurslar(query: String) {
        viewModelScope.launch {
            val results = kurslarRepository.searchCourses(query)
            _searchResults.postValue(results)
        }
    }

    fun addCourseToFavorites(kurs: Kurslar) {
        val currentFavorites = _favoriteCourses.value.orEmpty().toMutableList()
        currentFavorites.add(FavoriKurslar(kurs.kurs_id, kurs.kurs_isim, kurs.kurs_gorsel))
        _favoriteCourses.value = currentFavorites
        sharedPreferences.saveFavorites(currentFavorites)
    }

    fun removeCourseFromFavorites(kursId: Int) {
        val currentFavorites = _favoriteCourses.value.orEmpty().toMutableList()
        currentFavorites.removeAll { it.fav_kurs_id == kursId }
        _favoriteCourses.value = currentFavorites
        sharedPreferences.saveFavorites(currentFavorites)
    }

    fun getFavorites(): LiveData<List<FavoriKurslar>> {
        return favoriteCourses
    }

    fun updateFavorites(newFavorites: List<FavoriKurslar>) {
        _favoriteCourses.value = newFavorites
    }
}
