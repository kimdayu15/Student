package com.gems.student.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "course_name")
    val courseName: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "duration")
    val duration: Int
)