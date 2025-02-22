package com.example.examtrainer.presentation.ui.training

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.alpha
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.viewmodel.TrainingViewModel

enum class AnswerStatus {
    Correct,
    Wrong
}

@Composable
fun AnswersCountBox (count: Int, status: AnswerStatus) {
    val boxColor = when (status) {
        AnswerStatus.Correct -> MaterialTheme.colorScheme.surface
        AnswerStatus.Wrong -> MaterialTheme.colorScheme.errorContainer
    }

    Box(
        modifier = Modifier
            .size(30.dp)
            .background(
                color = boxColor,
                shape = RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$count",
            color = Color.Black,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun QuestionScreen(navController: NavController) {
    val viewModel: TrainingViewModel = viewModel()
    val questions by viewModel.questions.collectAsState()
    val index by viewModel.currentIndex.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()
    val correctAnswersCount by viewModel.correctAnswersCount.collectAsState()
    val wrongAnswersCount by viewModel.wrongAnswersCount.collectAsState()
    val time by viewModel.elapsedTime.collectAsState()
    val isAnswerConfirmed by viewModel.isAnswerConfirmed.collectAsState()
    val isHintUsed by viewModel.isHintUsed.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("main") {
                        launchSingleTop = true // Запуск только одного экземпляра
                    }
                }
                .padding(vertical = 10.dp, horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        navController.navigate("main") {
                            launchSingleTop = true // Запуск только одного экземпляра
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
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

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                AnswersCountBox(correctAnswersCount, AnswerStatus.Correct)
                Text(
                    text = "/",
                    color = Color.Black,
                    style = MaterialTheme.typography.labelMedium
                )
                AnswersCountBox(wrongAnswersCount, AnswerStatus.Wrong)
            }

            Text(
                text = String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60),
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Область вопроса
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(top=10.dp, start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "${index + 1}/${questions.size}",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Text(text = questions[index].text, fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(bottom=10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .alpha(if (!isHintUsed) 1f else 0f)
                        .clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .padding(5.dp)
                        .clickable (enabled = !isHintUsed) {
                            viewModel.useHint()
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.QuestionMark,
                        contentDescription = "Вопрос"
                    )
                }
            }
        }

        // Область ответов
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, bottom = 20.dp),

            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                questions[index].answers.forEachIndexed() { variantIndex, answer ->
                    var buttonColor = MaterialTheme.colorScheme.secondaryContainer
                     if (isAnswerConfirmed) {
                        if (answer == questions[index].correctAnswer) {
                            buttonColor = MaterialTheme.colorScheme.surface
                        } else {
                            if (answer == selectedAnswer) {
                                buttonColor = MaterialTheme.colorScheme.errorContainer
                            }
                        }
                    } else {
                        if (selectedAnswer == answer) {
                            buttonColor = MaterialTheme.colorScheme.primaryContainer
                        } else {
                            buttonColor = MaterialTheme.colorScheme.secondaryContainer
                        }
                    }
                    Button(
                        onClick = { viewModel.selectAnswer(answer) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.buttonElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${variantIndex+1}. ${answer}")
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .alpha(if (!isHintUsed) 0f else 1f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = questions[index].hint,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    if (isAnswerConfirmed) {
                        viewModel.nextQuestion()
                    }
                    else {
                        viewModel.confirmAnswer()
                    }
                },
                enabled = selectedAnswer != null,
                shape = RoundedCornerShape(10.dp),
//

            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 22.dp),
                    text = if (isAnswerConfirmed) "Далее" else "Подтвердить",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}