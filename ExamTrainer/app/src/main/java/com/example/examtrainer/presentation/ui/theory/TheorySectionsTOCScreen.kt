package com.example.examtrainer.presentation.ui.theory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.TOC
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.TheoryViewModel

@Composable
fun TheorySectionsTOCScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.THEORY_ROOT)
    val viewModel: TheoryViewModel = viewModel(backStackEntry)

    val chapters by viewModel.chapters.collectAsState()
    val currentChapterIdx by viewModel.currentChapterIdx.collectAsState()

    val sectionNames = chapters[currentChapterIdx].sections.map { s -> s.title }

    TOC(
        navController = navController,
        backButtonText = "Оглавление",
        backButtonRoute = NavRoutes.THEORY_CHAPTERS,
        items = sectionNames,
        onItemClick = { sectionIdx ->
            viewModel.selectSection(sectionIdx)
            navController.navigate(NavRoutes.THEORY_CONTENT) {
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}