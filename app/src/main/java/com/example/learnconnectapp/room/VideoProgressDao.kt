package com.example.learnconnectapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.data.entity.VideoProgress


@Dao
interface VideoProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(videoProgress: VideoProgress)

    @Query("SELECT * FROM video_progress WHERE course_id = :courseId AND user_id = :userId")
    suspend fun getVideoProgressForCourse(courseId: Int, userId: Int): List<VideoProgress>
}

