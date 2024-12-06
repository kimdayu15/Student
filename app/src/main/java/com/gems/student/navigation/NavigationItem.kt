package com.gems.student.navigation

sealed class NavigationItem(val route: String) {
    object StudentScreen : NavigationItem("student_screen/{studentId}")
    object RegScreen : NavigationItem("reg_screen")
}