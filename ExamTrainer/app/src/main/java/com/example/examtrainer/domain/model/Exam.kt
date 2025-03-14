package com.example.examtrainer.domain.model

data class Exam(
    val id: Int,
    val name: String
)

data class ExamsResponse(
    val all_exams: List<String>
)
