package com.example.examtrainer.presentation.viewmodel.exercise

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrainingViewModel : ExerciseViewModel() {
    // Флаг использованной подсказки
    private val _isHintUsed = MutableStateFlow<Boolean>(false)
    val isHintUsed: StateFlow<Boolean> = _isHintUsed

    fun useHint() {
        _isHintUsed.value = true
    }

    override fun confirmAnswer() {
        super.confirmAnswer()
        useHint()
    }

    override fun nextQuestion() {
        super.nextQuestion()
        _isHintUsed.value = false
    }
}