package com.example.learnconnectapp.room

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnconnectapp.data.entity.CommentsSimple
import com.example.learnconnectapp.data.entity.DownloadKurslarSimple
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.FavoriKurslarSimple
import com.example.learnconnectapp.data.entity.Kisiler
import java.util.concurrent.Flow


@Dao
interface KisilerDao  {


    @Query("SELECT * FROM kisiler WHERE kisi_email = :email AND kisi_sifre = :password")
    suspend fun getUser(email: String, password: String): Kisiler?


    @Query("SELECT * FROM kisiler")
    suspend fun tumKisiler(): List<Kisiler>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun kaydet(kisi:Kisiler)


    @Query("SELECT * FROM kisiler WHERE kisi_id = :kisiId")
    suspend fun kisiKurslariYukle(kisiId : Int) : List<Kisiler>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCourse(favKurs: FavoriKurslar)

    @Query("DELETE FROM favorikurslar WHERE fav_kurs_id = :favKursId")
    suspend fun removeFavoriteCourse(favKursId: Int)


    @Query("SELECT fav_kurs_id, fav_kurs_isim, imageLinks FROM favorikurslar WHERE fav_kurs_id = :favKursId")
    suspend fun kisiFavKursYukle(favKursId: Int): List<FavoriKurslar>


    @Query("SELECT kisi_kurs_indirme FROM kisiler WHERE kisi_id = :kisiId")
    suspend fun kisiKursIndirmeYukle(kisiId: Int) : List<DownloadKurslarSimple>


    @Query("SELECT kisi_kurs_yorum FROM kisiler WHERE kisi_id = :kisiId")
    suspend fun kisiKursYorum(kisiId: Int) : List<CommentsSimple>




}