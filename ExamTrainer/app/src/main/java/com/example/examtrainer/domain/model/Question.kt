package com.example.examtrainer.domain.model

data class ChapterQuestions(
    val title: String,
    val questions: List<Question>,
)

data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswer: String,
    val hint: String
)

data class ExamQuestion(
    val text: String,
    val answers: List<String>,
    val correctAnswer: String
)