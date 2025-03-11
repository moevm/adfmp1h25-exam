package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examtrainer.domain.model.StatisticData
import com.example.examtrainer.domain.model.Topic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StatsViewModel: ViewModel(){

    val _topics = MutableStateFlow<List<Topic>>(emptyList())
    val _general_stats = MutableStateFlow<StatisticData>(
        value = TODO()
    )

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

    fun load_general_stats(){
        viewModelScope.launch {
            _general_stats.value.readed_chapt = 3
            _general_stats.value.all_chapt = 5
            _general_stats.value.readed_topics = 5
            _general_stats.value.all_topics = 10
            _general_stats.value.answer_questions = 30
            _general_stats.value.all_questions = 300
            _general_stats.value.pass_exams = 3
            _general_stats.value.all_exams = 10
            _general_stats.value.training_count = 100
        }
    }

    fun get_chapt(): String{
        return _general_stats.value.readed_chapt.toString() + "/"+_general_stats.value.all_chapt.toString()
    }
    fun get_topic(): String{
        return _general_stats.value.readed_topics.toString() + "/"+_general_stats.value.all_topics.toString()
    }
    fun get_questions(): String{
        return _general_stats.value.answer_questions.toString() + "/"+_general_stats.value.answer_questions.toString()
    }
    fun get_exams(): String{
        return _general_stats.value.pass_exams.toString() + "/"+_general_stats.value.all_exams.toString()
    }
    fun get_trainings(): String{
        return _general_stats.value.training_count.toString()
    }


}