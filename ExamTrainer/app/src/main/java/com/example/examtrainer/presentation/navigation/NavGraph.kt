package com.example.examtrainer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.examtrainer.presentation.ui.main.MainScreen
import com.example.examtrainer.presentation.ui.exercise.exam.ExamStartScreen
import com.example.examtrainer.presentation.ui.exercise.exam.ExamQuestionScreen
import com.example.examtrainer.presentation.ui.exercise.exam.ExamResultScreen
import com.example.examtrainer.presentation.ui.exercise.training.TrainingChaptersStartScreen
import com.example.examtrainer.presentation.ui.theory.TheoryChaptersTOCScreen
import com.example.examtrainer.presentation.ui.exercise.training.TrainingQuestionScreen
import com.example.examtrainer.presentation.ui.exercise.training.TrainingResultScreen
import com.example.examtrainer.presentation.ui.exercise.training.TrainingStartScreen
import com.example.examtrainer.presentation.ui.stats.GeneralStatsScreen
import com.example.examtrainer.presentation.ui.stats.TopicsStudyStatsScreen
import com.example.examtrainer.presentation.ui.exercise.training.TrainingTOCScreen
import com.example.examtrainer.presentation.ui.info.InfoScreen
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.ui.theory.TheoryContentScreen
import com.example.examtrainer.presentation.ui.theory.TheorySectionsTOCScreen
import com.example.examtrainer.presentation.viewmodel.MainScreenViewModel
import com.example.examtrainer.presentation.viewmodel.exercise.TrainingTOCViewModel
import com.example.examtrainer.presentation.viewmodel.exercise.TrainingViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.MAIN
    ) {
        composable(NavRoutes.MAIN) { navBackStackEntry ->
            val viewModel: MainScreenViewModel = hiltViewModel(navBackStackEntry)
            MainScreen(navController, viewModel)
        }
        navigation(
            startDestination = NavRoutes.TRAINING_CHAPTERS,
            route = NavRoutes.TRAINING_ROOT
        ) {
            composable(NavRoutes.TRAINING_CHAPTERS) {
                TrainingTOCScreen(navController)
            }
            composable(NavRoutes.TRAINING_START) {
                TrainingStartScreen(navController)
            }
            composable(NavRoutes.TRAINING_CHAPTERS_START) {
                TrainingChaptersStartScreen(navController)
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
        navigation(
            startDestination = NavRoutes.STATS_GENERAL,
            route = NavRoutes.STATS_ROOT
        ) {
            composable(NavRoutes.STATS_GENERAL) {
                GeneralStatsScreen(navController)
            }
            composable(NavRoutes.STATS_TOPICS){
                TopicsStudyStatsScreen(navController)
            }
        }
        navigation(
            startDestination = NavRoutes.INFO,
            route = NavRoutes.INFO_ROOT
        ) {
            composable(NavRoutes.INFO) {
                InfoScreen(navController)
            }
        }
    }
}