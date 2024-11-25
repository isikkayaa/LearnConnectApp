package com.example.learnconnectapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "video_progress")
data class VideoProgress(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") @NotNull val id: Int = 0,
    @ColumnInfo(name = "user_id")  @NotNull val userId: Int,
    @ColumnInfo(name = "course_id") @NotNull val courseId: Int,
    @ColumnInfo(name = "video_id") @NotNull val videoId: Int,
    @ColumnInfo(name = "progress") @NotNull  val progress: Long
)
