package com.example.learnconnectapp.room

import androidx.room.Dao
import androidx.room.Query
import com.example.learnconnectapp.data.entity.Kisiler
import com.example.learnconnectapp.data.entity.Kurslar
@Dao
interface KurslarDao {


    /*@Query("SELECT kurs_isim FROM kurslar")
    suspend fun kurslariYukle() : List<Kurslar>

     */
}