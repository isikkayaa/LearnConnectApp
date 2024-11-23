package com.example.learnconnectapp.data.entity

data class DownloadKurslar(val download_kurs_id: Int,
                           val download_kurs_isim: String = "",
                           val imageLinks: ImageLinks? = null
)
