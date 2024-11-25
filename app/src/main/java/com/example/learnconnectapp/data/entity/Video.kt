package com.example.learnconnectapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "videos")
data class Video(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") @NotNull val id: Int = 0,
    @ColumnInfo(name = "course_id") @NotNull val courseId: Int,
    @ColumnInfo(name = "video_id") @NotNull val videoId: Int,
    @ColumnInfo(name = "video_title")@NotNull val videoTitle: String,
    @ColumnInfo(name = "video_url") val videoUrl: String
) : Serializable
