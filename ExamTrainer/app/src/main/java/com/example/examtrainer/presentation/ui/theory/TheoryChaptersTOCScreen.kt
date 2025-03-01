package com.example.examtrainer.presentation.ui.theory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.TheoryViewModel

@Composable
fun TheoryChaptersTOCScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.THEORY_ROOT)
    val viewModel: TheoryViewModel = viewModel(backStackEntry)

    val chapters by viewModel.chapters.collectAsState()

    val chapterNames = chapters.map { c -> c.title }

    TOC(
        navController = navController,
        backButtonText = "Главный экран",
        backButtonRoute = NavRoutes.MAIN,
        items = chapterNames,
        onItemClick = { chapterIdx ->
            viewModel.selectChapter(chapterIdx)
            navController.navigate(NavRoutes.THEORY_SECTIONS) {
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

