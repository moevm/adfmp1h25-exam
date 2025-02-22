package com.example.examtrainer.presentation.ui.training

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.state.TrainingScreenState
import com.example.examtrainer.presentation.viewmodel.TrainingViewModel

@Composable
fun TrainingScreen(navController: NavController) {
    val viewModel: TrainingViewModel = viewModel()
    val screenState by viewModel.screenState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars) // Отступ сверху
            .windowInsetsPadding(WindowInsets.navigationBars), // Отступ от нижней панели
    ) {
        when (screenState) {
            is TrainingScreenState.Welcome -> StartTrainingScreen(navController)
            is TrainingScreenState.Question -> QuestionScreen(navController)
            is TrainingScreenState.Result -> ResultScreen(navController)
        }
    }
}
