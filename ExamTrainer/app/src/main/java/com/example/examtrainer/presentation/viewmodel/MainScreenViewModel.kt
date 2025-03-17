package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrainer.data.local.ExamRepository
import com.example.examtrainer.data.local.StatsRepository
import com.example.examtrainer.domain.model.ExamItem
import com.example.examtrainer.domain.model.VisitStatistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val statsRepository: StatsRepository
) : ViewModel() {

    // Состояние списка экзаменов
    private val _exams = MutableStateFlow<List<ExamItem>>(emptyList())
    val exams: StateFlow<List<ExamItem>> = _exams

    // Состояние выбранного экзамена
    private val _selectedExam = MutableStateFlow<ExamItem?>(null)
    val selectedExam: StateFlow<ExamItem?> = _selectedExam

    // Состояние статистики выбранного экзамена
    private val _selectedExamVisitStatistics = MutableStateFlow<List<VisitStatistic>>(emptyList())
    val selectedExamVisitStatistics: StateFlow<List<VisitStatistic>> = _selectedExamVisitStatistics

    init {
        loadExams()
        loadSelectedExam()
        updateVisitStatistics()
    }

    fun loadExams() {
        viewModelScope.launch {
            _exams.value = examRepository.getAllExams()
        }
    }

    private fun loadSelectedExam() {
        viewModelScope.launch {
            var selectedExam = examRepository.getSelectedOrDefaultExam()
            if (_exams.value.none { it.name == selectedExam.name }) {
                // Если экзамен не найден, присваиваем дефолтный экзамен
                selectedExam = examRepository.getDefaultExam()
            }
            _selectedExam.value = selectedExam
            examRepository.saveSelectedExam(selectedExam)
        }
    }

    fun updateVisitStatistics() {
        val currentDate = LocalDate.now()
        statsRepository.setDayAttendance(currentDate.toString())
    }

    fun selectExam(exam: ExamItem) {
        viewModelScope.launch {
            _selectedExam.value = exam
            examRepository.saveSelectedExam(exam)
        }
    }

    fun getVisitStatistics() {
        val currentDate = LocalDate.now()
        val attendance: List<String> = statsRepository.getAttendance()

        val statistics = (0..6).map { dayOffset ->
            val date = currentDate.minusDays(dayOffset.toLong())
            VisitStatistic(
                date = date,
                isVisited = attendance.contains(date.toString())
            )
        }.reversed() // Для отображения последних дней слева направо

        _selectedExamVisitStatistics.value = statistics
    }
}