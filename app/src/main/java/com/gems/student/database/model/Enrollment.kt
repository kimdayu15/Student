package com.gems.student.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enrollments")
data class Enrollment (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "student_id")
    val studentId: Int,
    @ColumnInfo(name = "course_id")
    val courseId: Int,
    @ColumnInfo(name = "enrollment_date")
    val enrollmentDate: String
)