package com.example.examtrainer.presentation.ui.exercise.training

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.ui.exercise.StartExerciseInfoBox
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.exercise.TrainingTOCViewModel
import com.example.examtrainer.presentation.viewmodel.exercise.TrainingViewModel

@Composable
fun TrainingStartScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.TRAINING_ROOT)
    val tocViewModel: TrainingTOCViewModel = hiltViewModel(backStackEntry)
    val viewModel: TrainingViewModel = hiltViewModel(backStackEntry)

    val currentExam by tocViewModel.currentExam.collectAsState()
    val chapterQuestions by tocViewModel.chapterQuestions.collectAsState()
    val currentChapterIdx by tocViewModel.currentChapterIdx.collectAsState()

    if (currentChapterIdx >= 0) {
        val questions = chapterQuestions[currentChapterIdx].questions
        viewModel.loadQuestions(questions)
    }
//
//    println(currentChapterIdx)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars) // Отступ сверху
            .windowInsetsPadding(WindowInsets.navigationBars), // Отступ от нижней панели
    ) {
        CommonHeader(
            backButtonText = "Выход",
            onClick = {
                navController.navigate(NavRoutes.MAIN) {
                    launchSingleTop = true // Запуск только одного экземпляра
                }
            }
        )

        StartExerciseInfoBox(
            headerText = "Тренировка",
            infoText =
            """
                Вам будет предложено N вопросов по всему курсу “$currentExam”.
                
                В ходе решения экзамена Вы сможете получить подсказку по вопросу и посмотреть результат своего ответа.
            """.trimIndent(),
            onStart = {
                viewModel.startExercise()
                navController.navigate(NavRoutes.TRAINING_QUESTION) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
