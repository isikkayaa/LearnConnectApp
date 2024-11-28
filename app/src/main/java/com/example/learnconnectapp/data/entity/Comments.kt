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
                    @ColumnInfo(name = "courseImageUrl") @NotNull var courseImageUrl:Int,
                    @ColumnInfo(name = "rating") @NotNull var rating: Int
    )
