package com.example.examtrainer.presentation.ui.exercise.training

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.ui.draw.alpha
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.exercise.AnswersVariants
import com.example.examtrainer.presentation.ui.exercise.ConfirmButton
import com.example.examtrainer.presentation.ui.exercise.HintComponent
import com.example.examtrainer.presentation.ui.exercise.NextButton
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.TrainingViewModel
import java.util.Locale

enum class AnswerStatus {
    Correct,
    Wrong
}

@Composable
fun TrainingQuestionScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.TRAINING_ROOT)
    val viewModel: TrainingViewModel = viewModel(backStackEntry)

    val questions by viewModel.questions.collectAsState()
    val index by viewModel.currentIndex.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()
    val correctAnswersCount by viewModel.correctAnswersCount.collectAsState()
    val wrongAnswersCount by viewModel.wrongAnswersCount.collectAsState()
    val time by viewModel.elapsedTime.collectAsState()
    val isAnswerConfirmed by viewModel.isAnswerConfirmed.collectAsState()
    val isHintUsed by viewModel.isHintUsed.collectAsState()
    val defaultAnswerColor = MaterialTheme.colorScheme.secondaryContainer
    val selectedAnswerColor = MaterialTheme.colorScheme.primaryContainer
    val wrongAnswerColor = MaterialTheme.colorScheme.errorContainer
    val correctAnswerColor = MaterialTheme.colorScheme.surface

    val buttonBgColor: (String) -> Color = { answer ->
        when {
            isAnswerConfirmed && answer == questions[index].correctAnswer -> correctAnswerColor
            isAnswerConfirmed && answer == selectedAnswer -> wrongAnswerColor
            !isAnswerConfirmed && answer == selectedAnswer -> selectedAnswerColor
            else -> defaultAnswerColor
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars) // Отступ сверху
            .windowInsetsPadding(WindowInsets.navigationBars), // Отступ от нижней панели
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuestionScreenHeader(
            backButtonText = "Выход",
            onClick = {
                navController.navigate(NavRoutes.MAIN) {
                    launchSingleTop = true // Запуск только одного экземпляра
                }
            },
            time = time,
            correctAnswersCount = correctAnswersCount,
            wrongAnswersCount = wrongAnswersCount
        )

        // Область вопроса
        QuestionComponent(
            currentQuestionNumber = index + 1,
            questionsCount = questions.size,
            questionText = questions[index].text,
            isHintUsed = isHintUsed,
            onClickHint = { viewModel.useHint() }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnswersVariants(
                    answers = questions[index].answers,
                    onSelectAnswer = { answer -> viewModel.selectAnswer(answer) },
                    buttonBgColor = buttonBgColor
                )

                HintComponent(questions[index].hint, isHintUsed)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isAnswerConfirmed) {
            NextButton(
                text = "Далее",
                onClick = {
                    if (index < questions.size - 1)
                        viewModel.nextQuestion()
                    else {
                        viewModel.stopTraining()
                        navController.navigate(NavRoutes.TRAINING_RESULT, {
                            launchSingleTop = true
                            restoreState = true
                        })
                    }
                }
            )
        } else {
            ConfirmButton(
                text = "Подтвердить",
                enabled = selectedAnswer != null,
                onClick = {
                    viewModel.confirmAnswer()
                }
            )
        }
    }
}


@Composable
fun AnswersCountBox(count: Int, status: AnswerStatus) {
    val boxColor = when (status) {
        AnswerStatus.Correct -> MaterialTheme.colorScheme.surface
        AnswerStatus.Wrong -> MaterialTheme.colorScheme.errorContainer
    }

    Box(
        modifier = Modifier
            .size(30.dp)
            .background(
                color = boxColor,
                shape = RoundedCornerShape(5.dp)
            ),
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
fun QuestionScreenHeader(
    backButtonText: String,
    onClick: () -> Unit,
    time: Long,
    correctAnswersCount: Int,
    wrongAnswersCount: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick()
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
                text = backButtonText,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Row(
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
            text = String.format(
                Locale("ru", "RU"),
                "%02d:%02d:%02d",
                time / 3600,
                (time % 3600) / 60,
                time % 60
            ),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun QuestionComponent(
    currentQuestionNumber: Int,
    questionsCount: Int,
    questionText: String,
    isHintUsed: Boolean,
    onClickHint: () -> Unit
) {
    Column(
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
                .padding(top=10.dp, start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "${currentQuestionNumber}/${questionsCount}",
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = questionText,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                    .clickable(enabled = !isHintUsed) {
                        onClickHint()
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.QuestionMark,
                    contentDescription = "Вопрос"
                )
            }
        }
    }
}
