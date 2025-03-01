package com.example.examtrainer.presentation.ui.theory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.TheoryViewModel

@Composable
fun TheorySectionsTOCScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.THEORY_ROOT)
    val viewModel: TheoryViewModel = viewModel(backStackEntry)

    val chapters by viewModel.chapters.collectAsState()
    val currentChapterIdx by viewModel.currentChapterIdx.collectAsState()

    val sections = chapters[currentChapterIdx].sections

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        CommonHeader(
            backButtonText = "Оглавление",
            onClick = {
                navController.navigate(NavRoutes.THEORY_CHAPTERS) {
                    launchSingleTop = true
                }
            }
        )

        sections.forEachIndexed { sectionIdx, section ->
            TOCDivider()
            TOCButton(
                text = section.title,
                onClick = {
                    viewModel.selectSection(sectionIdx)
                    navController.navigate(NavRoutes.THEORY_CONTENT) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        TOCDivider()
    }
}


