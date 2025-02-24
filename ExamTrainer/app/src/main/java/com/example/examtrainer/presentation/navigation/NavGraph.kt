package com.example.examtrainer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.examtrainer.presentation.ui.MainScreen
import com.example.examtrainer.presentation.ui.exercise.exam.StartExamScreen
import com.example.examtrainer.presentation.ui.exercise.training.QuestionScreen
import com.example.examtrainer.presentation.ui.exercise.training.ResultScreen
import com.example.examtrainer.presentation.ui.exercise.training.StartTrainingScreen

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
        navigation(
            startDestination = "training-start",
            route = "training-root"
        ) {
            composable("training-start") {
                StartTrainingScreen(navController)
            }
            composable("training-question") {
                QuestionScreen(navController)
            }
            composable("training-result") {
                ResultScreen(navController)
            }
        }
        navigation(
            startDestination = "exam-start",
            route = "exam-root"
        ) {
            composable("exam-start") {
                StartExamScreen(navController)
            }
            composable("exam-question") {
                ExamQuestionScreen(navController)
            }
            composable("exam-result") {
                ExamResultScreen(navController)
            }
        }
    }
}