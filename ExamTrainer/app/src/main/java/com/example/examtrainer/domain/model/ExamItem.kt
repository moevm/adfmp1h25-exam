package com.example.examtrainer.domain.model

data class Exam(
    val id: Int,
    val name: String,
    val chapters: List<Chapter>,
)

