package com.example.learnconnectapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable


@Entity(tableName = "kurslar")
data class Kurslar(@PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "kurs_id" )@NotNull var kurs_id:Int,
    @ColumnInfo(name="kurs_isim") @NotNull var kurs_isim : String,
    @ColumnInfo(name = "kurs_gorsel") var kurs_gorsel:ImageLinks?) : Serializable

