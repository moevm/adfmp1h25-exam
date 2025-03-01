package com.example.examtrainer.presentation.ui.theory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.navigation.NavRoutes
import com.example.examtrainer.presentation.ui.CommonHeader
import com.example.examtrainer.presentation.ui.rememberRootBackStackEntry
import com.example.examtrainer.presentation.viewmodel.TheoryViewModel

@Composable
fun TheoryChaptersTOCScreen(navController: NavController) {
    val backStackEntry = rememberRootBackStackEntry(navController, NavRoutes.THEORY_ROOT)
    val viewModel: TheoryViewModel = viewModel(backStackEntry)

    val chapters by viewModel.chapters.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        CommonHeader(
            backButtonText = "Главный экран",
            onClick = {
                navController.navigate(NavRoutes.MAIN) {
                    launchSingleTop = true
                }
            }
        )

        chapters.forEachIndexed { chapterIdx, chapter ->
            TOCDivider()
            TOCButton(
                text = chapter.title,
                onClick = {
                    viewModel.selectChapter(chapterIdx)
                    navController.navigate(NavRoutes.THEORY_SECTIONS) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        TOCDivider()
    }
}

