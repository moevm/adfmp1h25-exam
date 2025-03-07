package com.example.examtrainer.presentation.ui.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
    var sortAscending by remember { mutableStateOf(true) }
    val sortedTopics by remember(sortAscending) {
        derivedStateOf {
            if (sortAscending) {
                topics.sortedBy { it.progress }
            } else {
                topics.sortedByDescending { it.progress }
            }
        }
    }

    // Находим max и min значения прогресса
    val maxProgress = remember(sortedTopics) { sortedTopics.maxOfOrNull { it.progress } ?: 0 }
    val minProgress = remember(sortedTopics) { sortedTopics.minOfOrNull { it.progress } ?: 0 }


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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Изученность тем",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = { sortAscending = !sortAscending }) {
            Icon(
                imageVector = if (sortAscending) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                contentDescription = "Сортировка"
            )
        }
    }
    // Список тем
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        this.items(
            items = sortedTopics,
            key = { it.name }
        ) { topic ->
            TopicRow(
                topic = topic,
                isMax = topic.progress == maxProgress,
                isMin = topic.progress == minProgress
            )
        }
    }
}

@Composable
private fun TopicRow(topic: Topic, isMax: Boolean, isMin: Boolean) {
    val textColor = when {
        isMax -> MaterialTheme.colorScheme.surface
        isMin -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onBackground
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = topic.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${topic.progress}%",
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}
