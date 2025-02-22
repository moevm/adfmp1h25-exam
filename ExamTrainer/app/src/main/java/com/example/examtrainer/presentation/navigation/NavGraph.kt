package com.example.examtrainer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examtrainer.presentation.ui.MainScreen
import com.example.examtrainer.presentation.ui.training.TrainingScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(navController)
        }
        composable("training") {
            TrainingScreen(navController)
        }
    }
}