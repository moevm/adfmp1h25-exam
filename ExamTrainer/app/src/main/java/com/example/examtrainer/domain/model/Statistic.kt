package com.example.examtrainer.domain.model

// Data class для тем
data class Topic(
    val name: String,
    val progress: Int
)

data class StatisticData(
    var readed_chapt: Int,
    var all_chapt: Int,
    var readed_topics: Int,
    var all_topics: Int,
    var answer_questions: Int,
    var all_questions: Int,
    var pass_exams: Int,
    var all_exams: Int,
    var training_count: Int
)