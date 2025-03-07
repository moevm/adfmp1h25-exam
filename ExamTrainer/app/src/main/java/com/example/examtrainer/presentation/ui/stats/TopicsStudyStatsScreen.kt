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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapVert
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
)

@Composable
fun TopicsStudyStatsScreen(navController: NavController) {
    var sortAscending by remember { mutableStateOf(true) }
    var isSortedByProgress by remember { mutableStateOf(false) } // Флаг сортировки по прогрессу

    val sortedTopics by remember(sortAscending, isSortedByProgress) {
        derivedStateOf {
            if (isSortedByProgress) {
                if (sortAscending) {
                    topics.sortedBy { it.progress }
                } else {
                    topics.sortedByDescending { it.progress }
                }
            } else {
               topics
            }
        }
    }

    val maxProgress = remember(sortedTopics) { sortedTopics.maxOfOrNull { it.progress } ?: 0 }
    val minProgress = remember(sortedTopics) { sortedTopics.minOfOrNull { it.progress } ?: 0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Верхняя панель
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .clickable { navController.navigate(NavRoutes.STATS_GENERAL) }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Назад",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Общая статистика",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Заголовок и кнопка сортировки
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
            IconButton(onClick = {
                isSortedByProgress = !isSortedByProgress
                if (isSortedByProgress) sortAscending = !sortAscending
            }) {
                Icon(
                    imageVector = Icons.Default.SwapVert,
                    contentDescription = "Сортировка",
                    tint = if (isSortedByProgress) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // Список тем
        LazyColumn(modifier = Modifier
            .padding(horizontal = 16.dp)
            .weight(1f)) {
            items(items = sortedTopics, key = { it.name }) { topic ->
                val backgroundColor = when {
                    topic.progress == maxProgress -> MaterialTheme.colorScheme.primaryContainer
                    topic.progress == minProgress -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.background
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor)
                ) {
                    TopicRow(topic = topic)
                }
            }
        }
    }
}

@Composable
private fun TopicRow(topic: Topic) {
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
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}