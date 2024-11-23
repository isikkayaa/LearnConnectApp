package com.example.learnconnectapp.data.entity

data class FavoriKurslar(
    val fav_kurs_id: Int,
    val fav_kurs_isim: String = "",
    val imageLinks: ImageLinks? = null
)
