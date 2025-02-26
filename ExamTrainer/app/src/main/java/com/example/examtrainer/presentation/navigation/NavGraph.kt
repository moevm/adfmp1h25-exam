package com.example.examtrainer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.examtrainer.presentation.ui.MainScreen
import com.example.examtrainer.presentation.ui.exercise.training.QuestionScreen
import com.example.examtrainer.presentation.ui.exercise.training.ResultScreen
import com.example.examtrainer.presentation.ui.exercise.training.StartScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.MAIN
    ) {
        composable(NavRoutes.MAIN) {
            MainScreen(navController)
        }
        navigation(
            startDestination = NavRoutes.TRAINING_START,
            route = NavRoutes.TRAINING_ROOT
        ) {
            composable(NavRoutes.TRAINING_START) {
                StartScreen(navController)
            }
            composable(NavRoutes.TRAINING_QUESTION) {
                QuestionScreen(navController)
            }
            composable(NavRoutes.TRAINING_RESULT) {
                ResultScreen(navController)
            }
        }
    }
}