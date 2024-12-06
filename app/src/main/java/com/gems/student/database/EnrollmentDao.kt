package com.gems.student.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gems.student.database.model.Enrollment


@Dao
interface EnrollmentDao {

    @Query("SELECT * FROM enrollments")
    fun getAllEnrollments(): List<Enrollment>


    @Query("SELECT * FROM enrollments")
    fun getEnrollmentById() : Enrollment

    @Insert
    fun addEnrollment(enrollment: Enrollment)

    @Query("SELECT id FROM enrollments ORDER BY id DESC LIMIT 1")
    fun getLastEnrollmentId(): Int?

}