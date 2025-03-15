package com.example.examtrainer.presentation.viewmodel.exercise

import androidx.lifecycle.viewModelScope
import com.example.examtrainer.data.local.ExamRepository
import com.example.examtrainer.data.local.TheoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExamViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val theoryRepository: TheoryRepository
) : ExerciseViewModel() {
    // Ограничение по времени
    private val _limitedTime = MutableStateFlow(20 * 60L) // 20 минут
    val limitedTime: StateFlow<Long> = _limitedTime

    // Проверка на приближение к ограничению по времени
    private val _isCloseToEnd = MutableStateFlow(false)
    val isCloseToEnd: StateFlow<Boolean> = _isCloseToEnd
    private val _closeThreshold: Long = 2 * 60L // 2 минуты

    // Проверка на конец экзамена
    private val _isEnd = MutableStateFlow(false)
    val isEnd: StateFlow<Boolean> = _isEnd

    private val _examThreshold = 0.5
    private val _successThreshold = MutableStateFlow(_examThreshold)
    val successThreshold: StateFlow<Boolean> = combine(
        correctAnswersCount,
        questions,
        _successThreshold
    ) { correctCount, questionsList, threshold ->
        (correctCount.toDouble() / questionsList.size) >= threshold
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false,
    )

    init {
        observeTimer()
        loadData()
    }

    override fun loadData() {
        val currentExam = examRepository.getSelectedExam()
        println(currentExam)
        // TODO: использовать при загрузке вопросов
        val questions = theoryRepository.getChapters(
            examRepository.getSelectedOrDefaultExam().name,
        )
            .map { c -> c.questions }
            .filter { q -> q.isNotEmpty() }
            .flatten()
        loadQuestions(questions)
    }

    private fun observeTimer() {
        viewModelScope.launch {
            elapsedTime.collect { elapsedTime ->
                _isCloseToEnd.update { (_limitedTime.value - elapsedTime) <= _closeThreshold }
                _isEnd.update { elapsedTime >= limitedTime.value }
            }
        }
    }
}
