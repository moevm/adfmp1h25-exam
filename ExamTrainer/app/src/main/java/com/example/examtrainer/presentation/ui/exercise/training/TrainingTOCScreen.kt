package com.example.examtrainer.presentation.ui.exercise.training

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.TOC
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.TrainingTOCViewModel

@Composable
fun TrainingTOCScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.TRAINING_ROOT)
    val viewModel: TrainingTOCViewModel = viewModel(backStackEntry)

    val chapterQuestions by viewModel.chapterQuestions.collectAsState()

    val chapterTitles = chapterQuestions.map { s -> s.title }

    TOC(
        navController = navController,
        backButtonText = "Главный экран",
        backButtonRoute = NavRoutes.MAIN,
        items = chapterTitles,
        onItemClick = { chapterIdx ->
            viewModel.selectChapter(chapterIdx)
            navController.navigate(NavRoutes.TRAINING_START) {
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}
