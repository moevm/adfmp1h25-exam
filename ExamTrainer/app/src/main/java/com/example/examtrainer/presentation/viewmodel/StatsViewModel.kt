package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrainer.domain.model.Topic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StatsViewModel: ViewModel(){

    val _topics = MutableStateFlow<List<Topic>>(emptyList())

    fun load_topics() {
        viewModelScope.launch {
            _topics.value = listOf(
                Topic("Тема 1", 89),
                Topic("Тема 2", 20),
                Topic("Тема 3", 79),
                Topic("Тема 4", 50),
                Topic("Тема 5", 61),
                Topic("Тема 6", 5),
                Topic("Тема 7", 15),
                Topic("Тема 8", 42),
                Topic("Тема 9", 30),
            )
        }

    }


}