package com.example.examtrainer.presentation.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppLifecycleObserver : LifecycleObserver {

    private val _isAppInForeground = MutableStateFlow(true)
    val isAppInForeground: StateFlow<Boolean> = _isAppInForeground

    init {
        // Регистрируем observer
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        // Приложение перешло в фоновый режим (свернуто)
        _isAppInForeground.value = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        // Приложение вернулось на передний план
        _isAppInForeground.value = true
    }

    fun removeObserver() {
        // Отписываемся от Lifecycle
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }
}