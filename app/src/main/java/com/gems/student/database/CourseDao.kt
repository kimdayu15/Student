package com.gems.student.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gems.student.database.model.Course

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getAllCourses(): List<Course>

    @Query("SELECT * FROM courses WHERE id = :courseId")
    fun getCourseById(courseId: Int): Course?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCourse(course: Course)

    @Query("SELECT id FROM courses ORDER BY id DESC LIMIT 1")
    fun getLastCourseId(): Int?
}