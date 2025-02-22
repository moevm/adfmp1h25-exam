package com.example.examtrainer.domain.model


data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswer: String,
    val hint: String
)
