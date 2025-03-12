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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.ChecklistRtl
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.example.examtrainer.domain.model.Exam
import com.example.examtrainer.presentation.navigation.NavRoutes


@Composable
fun TrainTypeButton(
    text: String,
    firstIcon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = firstIcon,
                contentDescription = "Иконка",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 15.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f),
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
fun InfoHeader(onClick: () -> Unit) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp, horizontal = 30.dp)

    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Info",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
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
            .padding(bottom = 0.dp)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .windowInsetsPadding(WindowInsets.statusBars),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(35.dp)
        ) {
            InfoHeader(
                onClick = {
                    navController.navigate(NavRoutes.INFO_ROOT) {
                        launchSingleTop = true
                    }
                }
            )
            StatisticWidget(
                onClick = {
                    navController.navigate(NavRoutes.STATS_GENERAL) {
                        launchSingleTop = true
                    }
                }
            )

            // Кнопка для выбора экзамена
            Box(
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
                    Row(
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
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background),
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    exams.forEach { exam ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = exam.name,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            },
                            onClick = {
                                viewModel.selectExam(exam)
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            TrainTypeButton(
                firstIcon = Icons.Filled.AutoStories,
                onClick = {
                    navController.navigate(NavRoutes.THEORY_ROOT) {
                        launchSingleTop = true
                    }
                },
                text = "Теория"
            )

            TrainTypeButton(
                firstIcon = Icons.Filled.NotificationImportant,
                onClick = {
                    navController.navigate("exam-root") {
                        launchSingleTop = true
                    }
                },
                text = "Экзамен",
            )

            TrainTypeButton(
                firstIcon = Icons.Filled.School,
                onClick = {
                    navController.navigate(NavRoutes.TRAINING_START) {
                        launchSingleTop = true
                    }
                },
                text = "Тренировка"
            )

            TrainTypeButton(
                firstIcon = Icons.Filled.ChecklistRtl,
                onClick = {
                    navController.navigate(NavRoutes.TRAINING_ROOT) {
                        launchSingleTop = true
                    }
                },
                text = "По темам",
            )
        }
    }
}
