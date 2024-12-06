package com.gems.student.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gems.student.database.model.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM students WHERE id = :studentId")
    fun getStudentById(studentId: Int): Student?

    @Insert
    fun addStudent(student: Student)

    @Query("SELECT id FROM students ORDER BY id DESC LIMIT 1")
    fun getLastStudentId(): Int?

    @Query("UPDATE students SET image = :imageResId WHERE id = :studentId")
    fun updateStudentImage(studentId: Int, imageResId: Int)
}