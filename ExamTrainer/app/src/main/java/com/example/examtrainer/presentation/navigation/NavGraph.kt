package com.example.examtrainer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.examtrainer.presentation.ui.MainScreen
import com.example.examtrainer.presentation.ui.exercise.exam.ExamStartScreen
import com.example.examtrainer.presentation.ui.exercise.exam.ExamQuestionScreen
import com.example.examtrainer.presentation.ui.exercise.exam.ExamResultScreen
import com.example.examtrainer.presentation.ui.theory.TheoryChaptersTOCScreen
import com.example.examtrainer.presentation.ui.exercise.training.TrainingQuestionScreen
import com.example.examtrainer.presentation.ui.exercise.training.TrainingResultScreen
import com.example.examtrainer.presentation.ui.exercise.training.TrainingStartScreen
import com.example.examtrainer.presentation.ui.theory.TheoryContentScreen
import com.example.examtrainer.presentation.ui.theory.TheorySectionsTOCScreen

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
                TrainingStartScreen(navController)
            }
            composable(NavRoutes.TRAINING_QUESTION) {
                TrainingQuestionScreen(navController)
            }
            composable(NavRoutes.TRAINING_RESULT) {
                TrainingResultScreen(navController)
            }
        }
        navigation(
            startDestination = NavRoutes.THEORY_CHAPTERS,
            route = NavRoutes.THEORY_ROOT,
        ) {
            composable(NavRoutes.THEORY_CHAPTERS) {
                TheoryChaptersTOCScreen(navController)
            }
            composable(NavRoutes.THEORY_SECTIONS) {
                TheorySectionsTOCScreen(navController)
            }
            composable(NavRoutes.THEORY_CONTENT) {
                TheoryContentScreen(navController)
            }
        }
        navigation(
            startDestination = NavRoutes.EXAM_START,
            route = NavRoutes.EXAM_ROOT
        ) {
            composable(NavRoutes.EXAM_START) {
                ExamStartScreen(navController)
            }
            composable(NavRoutes.EXAM_QUESTION) {
                ExamQuestionScreen(navController)
            }
            composable(NavRoutes.EXAM_RESULT) {
                ExamResultScreen(navController)
            }
        }
    }
}