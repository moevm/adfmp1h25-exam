package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrainer.domain.model.Exam
import com.example.examtrainer.domain.model.VisitStatistic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate


class MainScreenViewModel  : ViewModel() {

    // Состояние списка экзаменов
    private val _exams = MutableStateFlow<List<Exam>>(emptyList())
    val exams: StateFlow<List<Exam>> = _exams

    // Состояние выбранного экзамена
    private val _selectedExam = MutableStateFlow<Exam?>(null)
    val selectedExam: StateFlow<Exam?> = _selectedExam

    // Состояние статистики выбранного экзамена
    private val _selectedExamVisitStatistics = MutableStateFlow<List<VisitStatistic>>(emptyList())
    val selectedExamVisitStatistics: StateFlow<List<VisitStatistic>> = _selectedExamVisitStatistics

    // Функция для выбора экзамена
    fun selectExam(exam: Exam) {
        viewModelScope.launch {
            _selectedExam.value = exam
        }
    }

    // Функция для загрузки списка экзаменов
    fun loadExams() {
        viewModelScope.launch {
            // Здесь может быть загрузка данных из API или базы данных
            _exams.value = listOf(
                Exam(1, "Математика"),
                Exam(2, "Физика"),
                Exam(3, "Химия")
            )
        }
    }

    fun updateVisitStatistics() {
        val currentDate = LocalDate.now()

        val statistics = (0..6).map { dayOffset ->
            val date = currentDate.minusDays(dayOffset.toLong())
            VisitStatistic(
                date = date,
                isVisited = (dayOffset % 2 == 0) // Пример: чередование посещений
            )
        }.reversed() // Для отображения последних дней слева направо

        _selectedExamVisitStatistics.value = statistics
    }
}