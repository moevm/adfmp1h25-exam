package com.example.examtrainer.presentation.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppLifecycleObserver : DefaultLifecycleObserver {

    private val _isAppInForeground = MutableStateFlow(true)
    val isAppInForeground: StateFlow<Boolean> = _isAppInForeground

    init {
        // Регистрируем observer
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        // Приложение вернулось на передний план
        _isAppInForeground.value = true
    }

    override fun onStop(owner: LifecycleOwner) {
        // Приложение перешло в фоновый режим (свернуто)
        _isAppInForeground.value = false
    }

    fun removeObserver() {
        // Отписываемся от Lifecycle
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }
}