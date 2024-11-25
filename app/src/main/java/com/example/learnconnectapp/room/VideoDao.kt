package com.example.learnconnectapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnconnectapp.data.entity.Video

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos: List<Video>)

    @Query("SELECT * FROM videos WHERE course_id = :courseId")
    suspend fun getVideosByCourseId(courseId: Int): List<Video>


    @Query("SELECT video_url FROM videos WHERE course_id = :courseId AND video_id = :videoId")
    suspend fun getVideoUrl(courseId: Int, videoId: Int): String
}
