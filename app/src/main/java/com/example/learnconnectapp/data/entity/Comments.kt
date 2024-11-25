package com.example.learnconnectapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "comments")
data class Comments(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name="comment_id") @NotNull var comment_id: Int,
                    @ColumnInfo(name = "courseTitle") @NotNull var courseTitle : String,
                    @ColumnInfo(name="userComment") @NotNull var userComment: String,
                    @ColumnInfo(name = "courseImageUrl") var courseImageUrl: ImageLinks? = null,
                    @ColumnInfo(name = "rating") @NotNull var rating: Int
    )
