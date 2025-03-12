package com.example.examtrainer.domain.model

data class Section(
    val title: String,
    val content: String,
)

data class Chapter(
    val title: String,
    val sections: List<Section>,
    val questions: List<Question>,
)