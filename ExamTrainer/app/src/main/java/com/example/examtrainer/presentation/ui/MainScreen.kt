package com.example.examtrainer.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examtrainer.presentation.viewmodel.MainScreenViewModel
import androidx.navigation.NavController
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.examtrainer.domain.model.Exam


@Composable
fun TrainTypeButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier ) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(.85f)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer, // Цвет фона
            contentColor = Color.Black   // Цвет текста
        ),
        shape = RoundedCornerShape(10.dp), // Закругление углов
        elevation = ButtonDefaults.buttonElevation(4.dp) // Тень
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                modifier =  Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Стрелка вправо",
                modifier = Modifier
                    .size(28.dp)
            )
        }
    }
}


@Composable
fun MainScreen(navController: NavController) {
    // Получаем ViewModel
    val viewModel: MainScreenViewModel = viewModel()

    // Состояние для видимости выпадающего меню
    var isDropdownExpanded by remember { mutableStateOf(false) }

    // Получаем выбранный экзамен
    val selectedExam: Exam? = viewModel.selectedExam.collectAsState().value

    // Получаем список экзаменов
    val exams: List<Exam> = viewModel.exams.collectAsState().value


    // Загружаем список экзаменов при первом запуске
    LaunchedEffect(Unit) {
        viewModel.loadExams()
        viewModel.updateVisitStatistics()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
        ) {
            StatisticWidget(
                onClick = { navController.navigate("StartTestScreen") {
                    launchSingleTop = true
                } }
            )

            // Кнопка для выбора экзамена
            Box (
                modifier = Modifier
                    .width(275.dp)
                    .wrapContentHeight(Alignment.Top),
                contentAlignment = Alignment.Center

            ) {
                Button(
                    onClick = { isDropdownExpanded = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary, // Цвет фона
                        contentColor = Color.White   // Цвет текста
                    )
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = selectedExam?.name ?: "Выберите экзамен",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f), // Занимает оставшееся пространство
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = if (!isDropdownExpanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                            contentDescription = "Стрелка",
                            modifier = Modifier
                                .size(32.dp) // Размер иконки
                        )
                    }
                }

                // Выпадающее меню
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    exams.forEach { exam ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = exam.name,
                                    style = MaterialTheme.typography.labelLarge
                                )},
                            onClick = {
                                viewModel.selectExam(exam)
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

                TrainTypeButton(
                    onClick = {
                        navController.navigate("StartTestScreen") {
                            launchSingleTop = true
                        }
                    },
                    text = "Теория"
                )

                TrainTypeButton(
                    onClick = {
                        navController.navigate("StartTestScreen") {
                            launchSingleTop = true
                        }
                    },
                    text = "Экзамен",
                )

                TrainTypeButton(
                    onClick = {
                        navController.navigate("StartTestScreen") {
                            launchSingleTop = true
                        }
                    },
                    text = "Тренировка"
                )

                TrainTypeButton(
                    onClick = {
                        navController.navigate("StartTestScreen") {
                            launchSingleTop = true
                        }
                    },
                    text = "По темам",
                )
        }
    }
}
