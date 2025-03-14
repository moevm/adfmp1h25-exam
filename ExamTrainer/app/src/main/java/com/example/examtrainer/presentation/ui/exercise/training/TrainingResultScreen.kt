package com.example.examtrainer.presentation.ui.exercise.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.ui.exercise.BackToMainSreenButton
import com.example.examtrainer.presentation.ui.exercise.ShareButton
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.exercise.TrainingViewModel
import java.util.Locale

@Composable
fun TrainingResultScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.TRAINING_ROOT)
    val viewModel: TrainingViewModel = hiltViewModel(backStackEntry)

    val time by viewModel.elapsedTime.collectAsState()
    val questions by viewModel.questions.collectAsState()
    val correctAnswersCount by viewModel.correctAnswersCount.collectAsState()
    val wrongAnswersCount by viewModel.wrongAnswersCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom= 32.dp)
            .windowInsetsPadding(WindowInsets.statusBars) // Отступ сверху
            .windowInsetsPadding(WindowInsets.navigationBars), // Отступ от нижней панели
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CommonHeader(
            backButtonText = "Выход",
            onClick = {
                navController.navigate(NavRoutes.MAIN) {
                    launchSingleTop = true // Запуск только одного экземпляра
                }
            }
        )

        ResultsBox(time, questions.size, wrongAnswersCount, correctAnswersCount)

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            ShareButton(
                text = "Поделиться",
                shareText = "Текст для того, чтобы поделиться!"
            )

            BackToMainSreenButton(
                text = "На выход",
                onClick = {
                    navController.navigate(NavRoutes.MAIN) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


@Composable
fun ResultsBox(time: Long, questionsCount: Int, wrongAnswersCount: Int, correctAnswersCount: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth(.85f)
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f))
            .padding(25.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                text = "Результат тренировки",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = String.format(Locale("ru", "RU"), "Время: %02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Всего вопросов: ${questionsCount}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Неверно: ${wrongAnswersCount}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Верно: ${correctAnswersCount}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
