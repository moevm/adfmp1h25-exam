package com.example.examtrainer.presentation.ui.training

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.viewmodel.TrainingViewModel

@Composable
fun ResultScreen(navController: NavController) {
    val viewModel: TrainingViewModel = viewModel()
    val time by viewModel.elapsedTime.collectAsState()
    val questions by viewModel.questions.collectAsState()
    val correctAnswersCount by viewModel.correctAnswersCount.collectAsState()
    val wrongAnswersCount by viewModel.wrongAnswersCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom= 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        navController.navigate("main") {
                            launchSingleTop = true // Запуск только одного экземпляра
                        }
                    }
                    .padding(vertical = 20.dp, horizontal = 30.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Выход",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

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
                    text = String.format("Время: %02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Всего вопросов: ${questions.size}",
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

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Button(
                shape = RoundedCornerShape(10.dp),
                onClick = {viewModel.startTraining()}
            ) {
                Row(
//                    horizontalArrangement = Arrangement.spacedBy(10.dp),
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
    }
}
