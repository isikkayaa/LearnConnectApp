package com.example.learnconnectapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnconnectapp.data.entity.CurrentlyCourseList

@Dao
interface CurrentlyCourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentlyCourse(course: CurrentlyCourseList)

    @Query("SELECT * FROM currentlycourselist WHERE currently_kurs_id = :courseId")
    suspend fun getCurrentlyCourse(courseId: Int): CurrentlyCourseList?

    @Query("SELECT * FROM currentlycourselist")
    suspend fun getAllCurrentlyCourses(): List<CurrentlyCourseList>

    @Query("DELETE FROM currentlycourselist WHERE currently_kurs_id = :courseId")
    suspend fun deleteCurrentlyCourse(courseId: Int)
}
