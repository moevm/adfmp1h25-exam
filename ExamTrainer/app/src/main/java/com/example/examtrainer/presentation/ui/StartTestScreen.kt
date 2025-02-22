package com.example.examtrainer.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.examtrainer.presentation.viewmodel.StartTestScreenViewModel

@Composable
fun StartTestScreen (navController: NavController) {
    // Получаем ViewModel
    val viewModel: StartTestScreenViewModel = viewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Кнопка назад в левом верхнем углу
        Button(
            onClick = {
                navController.navigate("main") {
                    launchSingleTop = true // Запуск только одного экземпляра
//                    popUpTo("main") { inclusive = true }
                } },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Выход",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Это экран начала теста",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}