package com.example.examtrainer.presentation.ui.exercise.exam

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.rounded.DownloadDone
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.viewmodel.ExamViewModel

@Composable
fun ExamResultScreen(navController: NavController) {
    val backStackEntry = remember(navController) {
        navController.getBackStackEntry("exam-root") // Укажите общий ключ
    }
    val viewModel: ExamViewModel = viewModel(backStackEntry)

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
                navController.navigate("main") {
                    launchSingleTop = true // Запуск только одного экземпляра
                }
            }
        )

        resultsBox(time, questions.size, wrongAnswersCount, correctAnswersCount)

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            examShareButton()
            backToMainSreenButton(navController)
        }
    }
}


@Composable
fun resultsBox(time: Long, questionsCount: Int, wrongAnswersCount: Int, correctAnswersCount: Int) {
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
                text = "Экзамен сдан успешно!",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Icon(
                Icons.Rounded.DownloadDone,
                contentDescription = "Успех",
                modifier = Modifier.size(100.dp)
            )

            Text(
                text = String.format("Время: %02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60),
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

@Composable
fun examResult() {

}

@Composable
fun examShareButton() {
    val context = LocalContext.current
    Button(
        shape = RoundedCornerShape(10.dp),
        onClick = {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Текст для того, чтобы поделиться!")
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(shareIntent, "Поделиться через"))
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Share",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 22.dp),
                text = "Поделиться",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun backToMainSreenButton(navController: NavController) {
    Button(
        shape = RoundedCornerShape(10.dp),
        onClick = {
            navController.navigate("main") {
                launchSingleTop = true // Запуск только одного экземпляра
            }
        }
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 22.dp),
            text = "На выход",
            style = MaterialTheme.typography.bodySmall
        )
    }
}