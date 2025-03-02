package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrainer.domain.model.Question
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrainingViewModel : ViewModel() {
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
        loadQuestions() // Загружаем вопросы
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

    private fun loadQuestions() {
        _questions.value = listOf(
            Question("Что такое Kotlin?", listOf("Язык", "Фреймворк на подобии React View Angular и других веб фреймворках", "База данных (БД) — это имеющая название совокупность данных, которая отражает состояние объектов и их отношений в рассматриваемой предметной области.", "ОС"), "Язык", "Согласно главе 1 подглавы 2 п. 2.3. Kotlin (Ко́тлин) — кроссплатформенный, статически типизированный, объектно-ориентированный язык программирования, работающий поверх Java Virtual Machine и разрабатываемый компанией JetBrains. Также компилируется в JavaScript и в исполняемый код ряда платформ через инфраструктуру LLVM."),
            Question("Очень длинный и важныцй вопрос который требует супер мега внимательного чтения и обдумывания данная информация уже была описанна в некоторых главах теории поэтому просим хорошенько подумать над ответом иначе вы можете ответить не правильно и испортиьь себе статистику. Android основан на?", listOf("Windows", "Linux", "macOS", "DOS"), "Linux", "Согласно граве 1 подглаве 1 п. 1 Изначально разрабатывалась компанией Android, Inc., которую затем приобрела Google[7][8]. Основана на ядре Linux[9] и собственной реализации виртуальной машины Java компании Google. Впоследствии Google инициировала создание альянса Open Handset Alliance, который занимается поддержкой и дальнейшим развитием платформы. ")
        )
    }
}


