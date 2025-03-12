package com.example.examtrainer.presentation.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.ui.exercise.InfoHeader
import com.example.examtrainer.presentation.ui.exercise.InfoText
import com.example.examtrainer.presentation.ui.exercise.StartExerciseButton
import com.example.examtrainer.presentation.ui.exercise.StartExerciseInfoBox

@Composable
fun InfoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonHeader(
            backButtonText = "Назад",
            onClick = {
                navController.navigate(NavRoutes.MAIN) {
                    launchSingleTop = true
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(.85f)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f))
                .padding(25.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                InfoHeader(
                    text = "О приложении"
                )
                Spacer(modifier = Modifier.height(10.dp))
                InfoText(
                    text = "Фичи:\n" +
                            "-    Теория\n" +
                            "-    Режим тренировки (решаем рандомные н вопросов из общего пула)\n" +
                            "-    Режим экзамена (без подсказок и отображения правильности ответов)\n" +
                            "-    Режим тренировки по темам (решаем вопросы из конкретной темы)\n" +
                            "-    Статистика\n"
                )
                Spacer(modifier = Modifier.height(70.dp))
                InfoHeader(
                    text = "Разработчики"
                )
                Spacer(modifier = Modifier.height(10.dp))
                InfoText(
                    text = "Шаврин Алексей\n Кардаш Ярослав\n Ягодаров Михаил\n Заика Тимофей"
                )
            }
        }
    }
}