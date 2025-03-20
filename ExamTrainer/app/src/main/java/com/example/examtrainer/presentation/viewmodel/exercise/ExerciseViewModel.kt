package com.example.examtrainer.presentation.viewmodel.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrainer.domain.model.Question
import com.example.examtrainer.domain.utils.Timer
import com.example.examtrainer.presentation.lifecycle.AppLifecycleObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class ExerciseViewModel : ViewModel() {
    private val _appLifecycleObserver = AppLifecycleObserver()

    // Список вопросов
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    // Текущий вопрос (по индексу)
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    // Выбранный ответ
    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer: StateFlow<String?> = _selectedAnswer

    // Было ли подтверждение
    private val _isAnswerConfirmed = MutableStateFlow(false)
    val isAnswerConfirmed: StateFlow<Boolean> = _isAnswerConfirmed

    // Таймер
    private var isExerciseRunning: Boolean = false
    private val timer: Timer = Timer()
    val elapsedTime: StateFlow<Long> = timer.elapsedTime

    // Статистика
    private val _correctAnswersCount = MutableStateFlow(0)
    val correctAnswersCount: StateFlow<Int> = _correctAnswersCount

    private val _wrongAnswersCount = MutableStateFlow(0)
    val wrongAnswersCount: StateFlow<Int> = _wrongAnswersCount

    init {
        observeAppLifecycle()
    }

    abstract fun loadData()

//    private fun loadData() {
//        val questions = _repo.getChapters()
//            .map { c -> c.questions }
//            .filter { q -> q.isNotEmpty() }
//            .flatten()
//        loadQuestions(questions)
//    }

    private fun observeAppLifecycle() {
        viewModelScope.launch {
            _appLifecycleObserver.isAppInForeground.collect { isForeground ->
                if (isExerciseRunning && isForeground) {
                    timer.start(viewModelScope) // Возобновляем таймер
                } else {
                    timer.stop() // Останавливаем таймер
                }
            }
        }
    }

    fun loadQuestions(questions: List<Question>) {
        _questions.value = questions.shuffled()
    }

    fun startExercise() {
        isExerciseRunning = true
        timer.start(viewModelScope)
    }

    open fun stopExercise() {
        isExerciseRunning = false
        timer.stop()
    }

    fun pauseExercise() {
        timer.stop()
    }

    fun resumeExercise() {
        timer.start(viewModelScope)
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    open fun confirmAnswer() {
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

    open fun nextQuestion() {
        if (_currentIndex.value < _questions.value.size - 1) {
            _currentIndex.value += 1
            _selectedAnswer.value = null
            _isAnswerConfirmed.value = false
        }
    }

    open fun prevQuestion() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
            _selectedAnswer.value = null
            _isAnswerConfirmed.value = false
        }
    }

    override fun onCleared() {
        _appLifecycleObserver.removeObserver()
    }
}