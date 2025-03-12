package com.example.examtrainer.domain.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Timer {
    private val _elapsedTime = MutableStateFlow(0L);
    val elapsedTime: StateFlow<Long> = _elapsedTime;

    private var timerJob: Job? = null;

    fun start(scope: CoroutineScope) {
        timerJob?.cancel()
        timerJob = scope.launch {
            while (true) {
                delay(1000)
                _elapsedTime.value += 1
            }
        }
    }

    fun stop() {
        timerJob?.cancel()
        timerJob = null
    }

    fun reset() {
        _elapsedTime.value = 0
    }
}