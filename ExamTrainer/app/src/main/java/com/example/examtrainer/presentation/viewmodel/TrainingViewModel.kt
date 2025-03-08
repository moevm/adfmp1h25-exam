package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrainer.data.local.TheoryRepository
import com.example.examtrainer.domain.model.Question
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrainingViewModel : ViewModel() {
    private val _repo: TheoryRepository = TheoryRepository()

    // Список вопросов
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    // Текущий вопрос (по индексу)
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _isHintUsed = MutableStateFlow<Boolean>(false)
    val isHintUsed: StateFlow<Boolean> = _isHintUsed

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
        loadData()
    }

    fun loadQuestions(questions: List<Question>) {
        _questions.value = questions.shuffled()
    }

    fun startTraining() {
        startTimer()
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun useHint() {
        _isHintUsed.value = true
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
        _isHintUsed.value = true
    }

    fun nextQuestion() {
        if (_currentIndex.value < _questions.value.size - 1) {
            _currentIndex.value += 1
            _selectedAnswer.value = null
            _isAnswerConfirmed.value = false
            _isHintUsed.value = false
        }
    }

    fun stopTraining() {
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

    private fun loadData() {
        val questions = _repo.getChapters()
            .map { c -> c.questions }
            .filter { q -> q.isNotEmpty() }
            .flatten()
        loadQuestions(questions)
    }
}