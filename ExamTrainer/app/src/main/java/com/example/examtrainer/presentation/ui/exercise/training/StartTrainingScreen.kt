package com.example.examtrainer.presentation.ui.exercise.training

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
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.ui.exercise.StartExerciseInfoBox
import com.example.examtrainer.presentation.viewmodel.TrainingViewModel

@Composable
fun StartTrainingScreen (navController: NavController) {
    val backStackEntry = remember(navController) {
        navController.getBackStackEntry("training-root") // Укажите общий ключ
    }
    val viewModel: TrainingViewModel = viewModel(backStackEntry)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars) // Отступ сверху
            .windowInsetsPadding(WindowInsets.navigationBars), // Отступ от нижней панели
    ) {
        CommonHeader(
            backButtonText = "Выход",
            onClick = {
                navController.navigate("main") {
                    launchSingleTop = true // Запуск только одного экземпляра
                }
            }
        )

        StartExerciseInfoBox(
            headerText = "Тренировка",
            infoText = "Вам будет предложено N вопросов по всему курсу “Выбранный экзамен”.\n" +
                    "\n" +
                    "В ходе решения экзамена Вы сможете получить подсказку по вопросу и посмотреть результат своего ответа.",
            onStart = {
                viewModel.startTraining()
                navController.navigate("training-question") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
