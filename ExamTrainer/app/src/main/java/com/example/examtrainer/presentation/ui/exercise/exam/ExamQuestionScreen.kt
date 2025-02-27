package com.example.examtrainer.presentation.ui.exercise.exam

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.ExamViewModel
import java.util.Locale

@Composable
fun ExamQuestionScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.EXAM_ROOT)
    val viewModel: ExamViewModel = viewModel(backStackEntry)

    val questions by viewModel.questions.collectAsState()
    val index by viewModel.currentIndex.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()
    val time by viewModel.elapsedTime.collectAsState()
    val limitedTime by viewModel.limitedTime.collectAsState()
    val isCloseToEnd by viewModel.isCloseToEnd.collectAsState()
    val isEnd by viewModel.isEnd.collectAsState()
    val isAnswerConfirmed by viewModel.isAnswerConfirmed.collectAsState()
    val defaultAnswerColor = MaterialTheme.colorScheme.secondaryContainer
    val selectedAnswerColor = MaterialTheme.colorScheme.primaryContainer

    val buttonBgColor: (String) -> Color = { answer ->
        when {
            !isAnswerConfirmed && answer == selectedAnswer -> selectedAnswerColor
            else -> defaultAnswerColor
        }
    }

    if (isEnd) {
        viewModel.stopExam()
        navController.navigate(NavRoutes.EXAM_RESULT, {
            launchSingleTop = true
            restoreState = true
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars) // Отступ сверху
            .windowInsetsPadding(WindowInsets.navigationBars), // Отступ от нижней панели
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExamQuestionScreenHeader(
            backButtonText= "Выход",
            onClick = {
                navController.navigate(NavRoutes.MAIN) {
                    launchSingleTop = true // Запуск только одного экземпляра
                }
            },
            isCloseToEnd = isCloseToEnd,
            time = time,
            limitedTime = limitedTime
        )

        // Область вопроса
        ExamQuestionComponent(
            currentQuestionNumber = index+1,
            questionsCount = questions.size,
            questionText = questions[index].text
        )

        // Область ответов
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, bottom = 20.dp),

            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnswersVariants(
                answers = questions[index].answers,
                onSelectAnswer = { answer -> viewModel.selectAnswer(answer) },
                buttonBgColor = buttonBgColor
            )

            ConfirmButton (
                text = "Подтвердить",
                enabled = selectedAnswer != null,
                onClick = {
                    viewModel.confirmAnswer()
                    if (index < questions.size - 1)
                        viewModel.nextQuestion()
                    else {
                        viewModel.stopExam()
                        navController.navigate(NavRoutes.EXAM_RESULT, {
                            launchSingleTop = true
                            restoreState = true
                        })
                    }
                }
            )
        }
    }
}

@Composable
fun ExamQuestionScreenHeader(backButtonText: String, onClick: () -> Unit, isCloseToEnd: Boolean, time: Long, limitedTime: Long) {
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
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(
                    color = if (isCloseToEnd)
                            MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.65f)
                            else MaterialTheme.colorScheme.background.copy(alpha = 0f),
                    shape = RoundedCornerShape(5.dp)),
                contentAlignment = Alignment.Center
        ) {
            Text(
                text = String.format(
                    Locale("ru", "RU"),
                    "%02d:%02d/%02d:%02d",
                    (time % 3600) / 60, time % 60,
                    (limitedTime % 3600) / 60, limitedTime % 60),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

    }
}

@Composable
fun ExamQuestionComponent(currentQuestionNumber: Int, questionsCount: Int, questionText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(top=10.dp, start = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "${currentQuestionNumber}/${questionsCount}",
            style = MaterialTheme.typography.labelLarge,
        )
    }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = questionText, fontSize = 20.sp, modifier = Modifier.padding(8.dp))
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
                    .alpha(0f)
                    .clip(RoundedCornerShape(100))
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .padding(5.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.QuestionMark,
                    contentDescription = "Вопрос"
                )
            }
        }
    }
}
