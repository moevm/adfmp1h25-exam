package com.example.examtrainer.presentation.ui.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.domain.model.Topic
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.viewmodel.StatsViewModel
enum class SortState { NONE, DESCENDING, ASCENDING }

@Composable
fun TopicsStudyStatsScreen(navController: NavController) {
    val viewModel: StatsViewModel = hiltViewModel()


    var sortState by remember { mutableStateOf(SortState.NONE) }


    viewModel.load_topics()

    val sortedTopics by remember(sortState) {
        derivedStateOf {
            when (sortState) {
                SortState.NONE -> viewModel._topics.value // Нет сортировки
                SortState.DESCENDING -> viewModel._topics.value.sortedByDescending { it.progress }
                SortState.ASCENDING -> viewModel._topics.value.sortedBy { it.progress }
            }
        }
    }

    val maxProgress = remember(sortedTopics) { sortedTopics.maxOfOrNull { it.progress } ?: 0 }
    val minProgress = remember(sortedTopics) { sortedTopics.minOfOrNull { it.progress } ?: 0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonHeader("Общая статистика", onClick = {
            navController.navigate(NavRoutes.STATS_GENERAL){
                launchSingleTop = true
            }
        })

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
                sortState = when (sortState) {
                    SortState.NONE -> SortState.DESCENDING
                    SortState.DESCENDING -> SortState.ASCENDING
                    SortState.ASCENDING -> SortState.NONE
                }
            }) {
                Icon(
                    imageVector = Icons.Default.SwapVert,
                    contentDescription = "Сортировка",
                    modifier = Modifier.size(35.dp)
                )
            }
        }

        STOC(
            sortedTopics=sortedTopics,
            maxProgress=maxProgress,
            minProgress=minProgress
        )

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

@Composable
fun STOC(
    sortedTopics: List<Topic>,
    maxProgress: Int,
    minProgress: Int
) {
    // Список тем
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(.9f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = sortedTopics, key = { it.name }) { topic ->
            val backgroundColor = when {
                maxProgress == minProgress -> MaterialTheme.colorScheme.background
                topic.progress == maxProgress -> MaterialTheme.colorScheme.surface
                topic.progress == minProgress -> MaterialTheme.colorScheme.errorContainer
                else -> MaterialTheme.colorScheme.background
            }

            STOCDivider()
            STOCRow(
                topic = topic,
                bgColor = backgroundColor
            )
        }
    }
    STOCDivider()
}

@Composable
fun STOCRow(topic: Topic, bgColor: Color) {
    Button(
        onClick = {},
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = Color.Black
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = topic.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = "${topic.progress}%",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun STOCDivider() {
    HorizontalDivider(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
    )
}