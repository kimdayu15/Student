package com.gems.student.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gems.student.database.model.Course
import com.gems.student.database.model.Enrollment
import com.gems.student.database.model.Student

@Database(entities = [Student::class, Enrollment::class, Course::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun enrollmentDao(): EnrollmentDao
    abstract fun courseDao(): CourseDao
    abstract fun studentDao(): StudentDao


    companion object {
        private const val DATABASE_NAME = "reg_database"
        var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!

        }

    }
}