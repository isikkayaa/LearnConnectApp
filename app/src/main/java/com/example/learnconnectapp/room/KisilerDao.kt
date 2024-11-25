package com.example.learnconnectapp.room

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnconnectapp.data.entity.Kisiler
import java.util.concurrent.Flow


@Dao
interface KisilerDao  {


 /*   @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: Kisiler)

  */

    @Query("SELECT * FROM kisiler WHERE kisi_email = :email AND kisi_sifre = :password")
    suspend fun getUser(email: String, password: String): Kisiler?


    @Query("SELECT * FROM kisiler")
    suspend fun tumKisiler(): List<Kisiler>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun kaydet(kisi:Kisiler)


    @Query("SELECT * FROM kisiler WHERE kisi_id = :kisiId")
    suspend fun kisiKurslariYukle(kisiId : Int) : List<Kisiler>


   /* @Query("SELECT kisi_fav_kurs FROM kisiler")
    suspend fun kisiFavKursYukle() : List<Kisiler>

    @Query("SELECT kisi_kurs_indirme FROM kisiler")
    suspend fun kisiKursIndirmeYukle() : List<Kisiler>


    @Query("SELECT kisi_kurs_yorum FROM kisiler")
    suspend fun kisiKursYorum() : List<Kisiler>

    */




}