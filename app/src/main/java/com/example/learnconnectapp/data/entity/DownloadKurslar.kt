package com.example.learnconnectapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "downloadkurslar")
data class DownloadKurslar(@PrimaryKey(autoGenerate = true)
@ColumnInfo(name = "download_kurs_id") @NotNull var download_kurs_id: Int,
                           @ColumnInfo(name = "download_kurs_isim") @NotNull var download_kurs_isim: String,
                           @ColumnInfo(name = "imageLinks") var  imageLinks: ImageLinks? = null) :Serializable
