package com.example.examtrainer.domain.model

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class VisitStatistic(
    val date: LocalDate,
    val isVisited: Boolean
) {
    val dayOfWeek: String
        get() = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru"))
}
