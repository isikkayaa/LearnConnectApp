package com.example.learnconnectapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable


@Entity(tableName = "kisiler")
data class Kisiler(@PrimaryKey(autoGenerate = true)
                   @ColumnInfo(name = "kisi_id") @NotNull var kisi_id : Int,
                   @ColumnInfo(name = "kisi_username") @NotNull var kisi_username : String,
                   @ColumnInfo(name = "kisi_email") @NotNull var kisi_email : String,
                   @ColumnInfo(name = "kisi_sifre") @NotNull var kisi_sifre : String,
                   @ColumnInfo(name = "kisi_kurs_isim") @NotNull var kisi_kurs_isim : String,
                   @ColumnInfo(name = "kisi_video_ilerleme") @NotNull var kisi_video_ilerleme : Int,
                   @ColumnInfo(name = "kisi_fav_kurs") @NotNull var kisi_fav_kurs : String,
                   @ColumnInfo(name = "kisi_kurs_yorum") @NotNull var kisi_kurs_yorum : String,
                   @ColumnInfo(name = "kisi_kurs_puan") @NotNull var kisi_kurs_puan : Int,
                   @ColumnInfo(name = "kisi_kurs_indirme") @NotNull var kisi_kurs_indirme : String) : Serializable
