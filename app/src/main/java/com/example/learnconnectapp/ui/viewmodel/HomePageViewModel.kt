package com.example.learnconnectapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.data.repository.KurslarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(private val kurslarRepository: KurslarRepository,
    @ApplicationContext private val context: Context) : ViewModel() {

    private val _kurslarListesi = MutableLiveData<List<Kurslar>?>()
    val kurslarListesi: LiveData<List<Kurslar>?> get() = _kurslarListesi

    private val _favKurslar = MutableLiveData<List<FavoriKurslar>>()
    val favKurslar: LiveData<List<FavoriKurslar>> get() = _favKurslar

    private val _favoriKurslar = MutableLiveData<List<Kurslar>?>()
    val favoriKurslar : LiveData<List<Kurslar>?> get()  = _favoriKurslar


    init {

    }




}