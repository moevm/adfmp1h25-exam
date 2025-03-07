package com.example.examtrainer.presentation.ui.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes

// Data class для тем
data class Topic(
    val name: String,
    val progress: Int
)

// Хардкоженный список тем
val topics = listOf(
    Topic("Тема 1", 89),
    Topic("Тема 2", 20),
    Topic("Тема 3", 79),
    Topic("Тема 4", 50),
    Topic("Тема 5", 61),
    Topic("Тема 6", 5),
    Topic("Тема 7", 15),
    Topic("Тема 8", 42),
    Topic("Тема 9", 30),
    Topic("Тема 10", 11)
)

@Composable
fun TopicsStudyStatsScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Верхняя панель с кнопкой назад
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .clickable { navController.navigate(NavRoutes.STATS_GENERAL) }
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Назад",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Общая статистика",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        }
}