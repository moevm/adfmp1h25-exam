@file:Suppress("UNREACHABLE_CODE")

package com.example.examtrainer.presentation.ui.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.viewmodel.StatsViewModel

@Composable
fun GeneralStatsScreen(navController: NavController) {

    val viewModel: StatsViewModel = hiltViewModel()
    val configuration = LocalConfiguration.current
    viewModel.load_general_stats()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonHeader("Главный экран", onClick = {
            navController.navigate(NavRoutes.MAIN){
                launchSingleTop = true
            }
        })

        // Заголовок
        Text(
            text = "За все время вы:",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )

        // Плашки статистики
        StatItem(
            icon = Icons.Default.Book, // Иконка для глав
            title = "Прочитали глав",
            value = viewModel.get_chapt()
        )

//        StatItem(
//            icon = Icons.Default.Checklist, // Иконка для тем
//            title = "Прошли тем",
//            value = viewModel.get_topic()
//        )

//        StatItem(
//            icon = Icons.Default.QuestionMark, // Иконка для вопросов
//            title = "Решили вопросов",
//            value = viewModel.get_questions()
//        )

        StatItem(
            icon = Icons.Default.School, // Иконка для экзаменов
            title = "Сдали экзаменов",
            value = viewModel.get_exams()
        )

        StatItem(
            icon = Icons.Default.FactCheck, // Иконка для тренировок
            title = "Прошли тренировок",
            value = viewModel.get_trainings()
        )

        // Кликабельная плашка для изученности тем
        ClickableStatItem(
            icon = Icons.Default.FactCheck, // Иконка для тем
            title = "Изученность тем",
            onClick = { navController.navigate(NavRoutes.STATS_TOPICS){
                launchSingleTop = true
            } }
        )

        // Нижний спейсер для заполнения пространства
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StatItem(icon: ImageVector, title: String, value: String) {

    val contentColor = Color(0, 0, 0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer, // Фон не кликабельных плашек
            contentColor = contentColor
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor.copy(alpha = 0.8f)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor,
                )
            }
        }
    }
}

@Composable
private fun ClickableStatItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer, // Фон кликабельной плашки
            contentColor = Color(0, 0, 0) // Цвет контента
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Перейти",
            )
        }
    }
}