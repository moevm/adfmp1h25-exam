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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.FactCheck
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.exercise.AnswersVariants
import com.example.examtrainer.presentation.ui.exercise.ConfirmButton
import com.example.examtrainer.presentation.ui.exercise.ConfirmExitDialog
import com.example.examtrainer.presentation.ui.exercise.HintComponent
import com.example.examtrainer.presentation.ui.exercise.TrainingButton
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.exercise.TrainingViewModel
import java.util.Locale

enum class AnswerStatus {
    Correct,
    Wrong
}

@Composable
fun TrainingQuestionScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.TRAINING_ROOT)
    val viewModel: TrainingViewModel = hiltViewModel(backStackEntry)

    var showDialog by remember { mutableStateOf(false) }
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
    val questionsHistory by viewModel.questionsHistory.collectAsState()

    val buttonBgColor: (String) -> Color = { answer ->
        val state = questionsHistory[index]?.get(answer)
        when {
            isAnswerConfirmed && answer == questions[index].correctAnswer ||
                    state == TrainingViewModel.AnswerState.Correct
                -> correctAnswerColor
            isAnswerConfirmed && answer == selectedAnswer ||
                    state == TrainingViewModel.AnswerState.Wrong
                -> wrongAnswerColor
            !isAnswerConfirmed && answer == selectedAnswer -> selectedAnswerColor
            else -> defaultAnswerColor
        }
    }

    if (showDialog) {
        ConfirmExitDialog(
            titleText = "Внимание",
            text = "Вы уверены что хотите закончить тренировку?",
            onDismiss = {
                showDialog = false
                viewModel.resumeExercise()
            },
            onConfirm = {
                navController.navigate(NavRoutes.MAIN) {
                    launchSingleTop = true
                }
            }
        )
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
                showDialog = true
                viewModel.pauseExercise()
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
                    buttonBgColor = buttonBgColor,
                    interactable = !questionsHistory.containsKey(index),
                )

                HintComponent(questions[index].hint, isHintUsed)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            TrainingButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = {
                    viewModel.prevQuestion()
                },
                enabled = index != 0,
            )
            ConfirmButton(
                text = "Подтвердить",
                enabled = !isAnswerConfirmed && selectedAnswer != null,
                onClick = {
                    viewModel.confirmAnswer()
                }
            )
            if (index != questions.size - 1) {
                TrainingButton(
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    onClick = {
                        viewModel.nextQuestion()
                    },
                )
            } else {
                TrainingButton(
                    icon = Icons.AutoMirrored.Filled.FactCheck,
                    onClick = {
                        viewModel.stopExercise()
                        navController.navigate(NavRoutes.TRAINING_RESULT, {
                            launchSingleTop = true
                            restoreState = true
                        })
                    },
                )
            }
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
                .padding(top = 10.dp, start = 10.dp),
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
                .padding(bottom = 10.dp, end = 10.dp),
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
