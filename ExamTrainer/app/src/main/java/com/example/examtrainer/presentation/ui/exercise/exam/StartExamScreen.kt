package com.example.examtrainer.presentation.ui.exercise.exam

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.ui.exercise.StartExerciseInfoBox
import com.example.examtrainer.presentation.viewmodel.ExamViewModel

@Composable
fun StartExamScreen (navController: NavController) {
    val backStackEntry = remember(navController) {
        navController.getBackStackEntry(NavRoutes.EXAM_ROOT) // Укажите общий ключ
    }
    val viewModel: ExamViewModel = viewModel(backStackEntry)

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
            headerText = "Экзамен",
            infoText = "Вам будет предложено N вопросов по всему курсу “Выбранный экзамен”.\n" +
                    "\n" +
                    "В ходе решения экзамена Вы не сможете получить подсказку по вопросу или посмотреть результат своего ответа.",
            onStart = {
                viewModel.startExam()
                navController.navigate(NavRoutes.EXAM_QUESTION) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
