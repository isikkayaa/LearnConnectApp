package com.example.learnconnectapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable


@Entity(tableName = "currentlycourselist")
data class CurrentlyCourseList(@PrimaryKey(autoGenerate = true)
                               @ColumnInfo(name = "currently_kurs_id") @NotNull var currently_kurs_id : Int,
                               @ColumnInfo(name = "currently_kurs_isim") @NotNull var currently_kurs_isim: String,
                               @ColumnInfo(name = "imageLinks") @NotNull var imageLinks: Int ) :Serializable
