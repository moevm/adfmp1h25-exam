package com.example.examtrainer.presentation.ui.theory

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.TheoryViewModel
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun TheoryContentScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.THEORY_ROOT)
    val viewModel: TheoryViewModel = hiltViewModel(backStackEntry)

    val chapters by viewModel.chapters.collectAsState()
    val currentChapterIdx by viewModel.currentChapterIdx.collectAsState()
    val currentSectionIdx by viewModel.currentSectionIdx.collectAsState()

    val content = chapters[currentChapterIdx].sections[currentSectionIdx].content

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        CommonHeader(
            backButtonText = "Подразделы",
            onClick = {
                navController.navigate(NavRoutes.THEORY_SECTIONS) {
                    launchSingleTop = true
                }
            }
        )

        MarkdownText(
            markdown = content,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 22.dp)
                .verticalScroll(ScrollState(0)),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}


