package com.example.learnconnectapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.Video


@Dao
interface CommentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<Comments>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comment: Comments)

    @Query("SELECT * FROM comments WHERE courseTitle = :courseTitle")
    suspend fun getCommentsByCourseTitle(courseTitle: String): List<Comments>

    @Query("SELECT * FROM comments WHERE comment_id = :commentId")
    suspend fun getCommentsByUser(commentId:Int): List<Comments>


}
