package com.example.examtrainer.presentation.ui.main


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examtrainer.domain.model.Exam
import com.example.examtrainer.presentation.viewmodel.MainScreenViewModel

@Composable
fun StatisticWidget (onClick: () -> Unit) {
    // Получаем ViewModel
    val viewModel: MainScreenViewModel = viewModel()

    // Получаем выбранный экзамен
    val selectedExam: Exam? = viewModel.selectedExam.collectAsState().value

    // Получаем статистику по экзамену
    val selectedExamVisitStatistics by viewModel.selectedExamVisitStatistics.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth(.85f)
            .fillMaxHeight(.25f)
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Статистика по экзамену: \n\"")
                    append(selectedExam?.name ?: "Не выбран")
                    append("\"")
                },
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .height(100.dp)
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                selectedExamVisitStatistics.forEachIndexed { index, stat ->
                    val backgroundColor = if (index == selectedExamVisitStatistics.size - 1) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Transparent
                    }
                    val textColor = if (index == selectedExamVisitStatistics.size - 1) {
                        Color.White
                    } else {
                        Color.Black
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .background(backgroundColor),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = if (stat.isVisited) Icons.Filled.TaskAlt else Icons.Filled.Close,
                            contentDescription = if (stat.isVisited) "Посещен" else "Не посещен",
                            modifier = Modifier.size(24.dp),
                            tint = if (stat.isVisited) Color.Green else Color.Red
                        )
                        Text(
                            text = stat.dayOfWeek,
                            color = textColor,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}