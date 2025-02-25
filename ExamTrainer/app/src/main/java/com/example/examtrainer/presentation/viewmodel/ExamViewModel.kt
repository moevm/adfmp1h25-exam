package com.example.examtrainer.presentation.viewmodel;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrainer.domain.model.ExamQuestion
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExamViewModel : ViewModel() {
    // Список вопросов
    private val _questions = MutableStateFlow<List<ExamQuestion>>(emptyList())
    val questions: StateFlow<List<ExamQuestion>> = _questions

    // Текущий вопрос (по индексу)
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    // Выбранный ответ
    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer: StateFlow<String?> = _selectedAnswer

    // Было ли подтверждение
    private val _isAnswerConfirmed = MutableStateFlow<Boolean>(false)
    val isAnswerConfirmed: StateFlow<Boolean> = _isAnswerConfirmed

    // Таймер
    private val _elapsedTime = MutableStateFlow(0L) // в секундах
    val elapsedTime: StateFlow<Long> = _elapsedTime
    private var timerJob: Job? = null

    // Статистика
    private val _correctAnswersCount = MutableStateFlow(0)
    val correctAnswersCount: StateFlow<Int> = _correctAnswersCount

    private val _wrongAnswersCount = MutableStateFlow(0)
    val wrongAnswersCount: StateFlow<Int> = _wrongAnswersCount

    init {
        loadQuestions() // Загружаем вопросы
    }

    fun startExam() {
        startTimer()
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun confirmAnswer() {
        if (_selectedAnswer.value == null) {
            return
        }
        _selectedAnswer.value?.let { selected ->
            val correct = questions.value[currentIndex.value].correctAnswer
            if (correct == selected) {
                _correctAnswersCount.value += 1
            } else {
                _wrongAnswersCount.value += 1
            }
        }
        _isAnswerConfirmed.value = true
    }

    fun nextQuestion() {
        if (_currentIndex.value < _questions.value.size - 1) {
            _currentIndex.value += 1
            _selectedAnswer.value = null
            _isAnswerConfirmed.value = false
        }
    }

    fun stopExam() {
        stopTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _elapsedTime.value += 1
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    private fun loadQuestions() {
        _questions.value = listOf(
            ExamQuestion("Что такое Kotlin?", listOf("Язык", "Фреймворк", "БД", "ОС"), "Язык"),
            ExamQuestion("Android основан на?", listOf("Windows", "Linux", "macOS", "DOS"), "Linux")
        )
    }
}
