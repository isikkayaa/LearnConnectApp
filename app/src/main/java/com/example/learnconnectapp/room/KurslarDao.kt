package com.example.learnconnectapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnconnectapp.data.entity.CurrentlyCourseList
import com.example.learnconnectapp.data.entity.Kisiler
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.data.entity.Video

@Dao
interface KurslarDao {


    @Query("SELECT * FROM kurslar")
    suspend fun kurslariYukle() : List<Kurslar>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(kurslar: List<Kurslar>)


    @Query("SELECT * FROM kurslar WHERE kurs_isim LIKE '%' || :query || '%'")
    suspend fun searchCourses(query: String): List<Kurslar>


    @Query("SELECT * FROM currentlycourselist WHERE currently_kurs_id = :userId")
    suspend fun getCurrentlyCoursesByUser(userId: Int): List<CurrentlyCourseList>



    @Query("SELECT * FROM kurslar WHERE kurs_id = :courseId")
    suspend fun getCourseById(courseId: Int): Kurslar?
    }

