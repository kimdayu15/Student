package com.gems.student.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gems.student.ui.screen.RegScreen
import com.gems.student.ui.screen.StudentsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.RegScreen.route
) {
    NavHost(modifier = Modifier,
        navController = navController,
        startDestination = startDestination){

        composable(NavigationItem.StudentScreen.route){
            StudentsScreen(navController)
        }

        composable(NavigationItem.RegScreen.route){
            RegScreen(navController)
        }

    }

}