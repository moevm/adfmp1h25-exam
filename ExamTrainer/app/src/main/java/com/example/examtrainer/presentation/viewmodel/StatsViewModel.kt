package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrainer.data.local.ExamRepository
import com.example.examtrainer.data.local.StatsRepository
import com.example.examtrainer.data.local.model.GeneralStats
import com.example.examtrainer.data.local.model.QuestionStat
import com.example.examtrainer.data.local.model.StatsFields
import com.example.examtrainer.domain.model.StatisticData
import com.example.examtrainer.domain.model.Topic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val statsRepository: StatsRepository
): ViewModel(){

    val _topics = MutableStateFlow<List<Topic>>(emptyList())
    val _general_stats = MutableStateFlow<GeneralStats>(
        value = statsRepository.getGeneralStats(examRepository.getSelectedExam()!!.name)
    )

    fun load_topics() {
        viewModelScope.launch {
            _topics.value =  transformToTopics(_general_stats.value.question)
        }

    }

    private fun transformToTopics(questionMap: MutableMap<String, MutableMap<String, QuestionStat>>): List<Topic> {
        return questionMap.map { (topicName, questions) ->
            // Вычисляем сумму all и right для всех вопросов в теме
            val totalAll = questions.values.sumOf { it.all }
            val totalRight = questions.values.sumOf { it.right }

            // Вычисляем прогресс в процентах
            val progress = if (totalAll > 0) {
                (totalRight.toDouble() / totalAll.toDouble() * 100).toInt()
            } else {
                0 // Если вопросов нет, прогресс 0%
            }

            // Создаем объект Topic
            Topic(name = topicName, progress = progress)
        }
    }

    fun load_general_stats(){
        viewModelScope.launch {
            _general_stats.value = statsRepository.getGeneralStats(examRepository.getSelectedExam()!!.name)
        }
    }

    fun get_chapt(): String{
        return _general_stats.value.getReadChaptersCount().toString() + "/" + _general_stats.value.getTotalChaptersCount().toString()//_general_stats.value.read_chapt.toString() + "/"+_general_stats.value.all_chapt.toString()
    }
    fun get_topic(): String{
        return ""//_general_stats.value.read_topics.toString() + "/"+_general_stats.value.all_topics.toString()
    }
    fun get_questions(): String{
        return ""//_general_stats.value.answer_questions.toString() + "/"+_general_stats.value.all_questions.toString()
    }
    fun get_exams(): String{
        return _general_stats.value.pass_exams.toString() + "/"+_general_stats.value.all_exams.toString()
    }
    fun get_trainings(): String{
        return _general_stats.value.training_count.toString()
    }


}