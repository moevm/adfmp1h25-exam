package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrainer.domain.model.Exam
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch




class MainScreenViewModel  : ViewModel() {
    // Состояние выбранного экзамена
    private val _selectedExam = MutableStateFlow<Exam?>(null)
    val selectedExam: StateFlow<Exam?> = _selectedExam

    // Состояние списка экзаменов
    private val _exams = MutableStateFlow<List<Exam>>(emptyList())
    val exams: StateFlow<List<Exam>> = _exams

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
}