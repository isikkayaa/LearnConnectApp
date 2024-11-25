package com.example.learnconnectapp.room

import androidx.room.Dao
import androidx.room.Query
import com.example.learnconnectapp.data.entity.DownloadKurslar


@Dao
interface DownloadDao {
    @Query("SELECT * FROM downloadkurslar")
    suspend fun getAllDownloads(): List<DownloadKurslar>
}
