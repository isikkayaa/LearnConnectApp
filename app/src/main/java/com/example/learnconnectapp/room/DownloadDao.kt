package com.example.learnconnectapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnconnectapp.data.entity.DownloadKurslar


@Dao
interface DownloadDao {
    @Query("SELECT * FROM downloadkurslar")
    suspend fun getAllDownloads(): List<DownloadKurslar>

    @Query("SELECT * FROM downloadkurslar WHERE download_kurs_id = :courseId")
    suspend fun getDownloadByCourseId(courseId: Int): DownloadKurslar?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(downloadKurs: DownloadKurslar)

    @Delete
    suspend fun delete(downloadKurs: DownloadKurslar)
}