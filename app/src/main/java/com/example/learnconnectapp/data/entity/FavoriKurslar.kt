package com.example.learnconnectapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "favorikurslar")
data class FavoriKurslar(@PrimaryKey(autoGenerate = true)
                         @ColumnInfo(name = "fav_kurs_id") @NotNull val fav_kurs_id: Int,
                         @ColumnInfo(name = "fav_kurs_isim") @NotNull val fav_kurs_isim: String,
                         @ColumnInfo(name = "imageLinks") val imageLinks: ImageLinks? = null
)
