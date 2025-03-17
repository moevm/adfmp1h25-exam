package com.example.examtrainer.data.local.model

import com.example.examtrainer.domain.model.StatisticData
import com.google.gson.annotations.SerializedName

data class QuestionStat (
    var all: Int,
    var right: Int
)
data class GeneralStats(
    var theory: MutableMap<String, MutableMap<String, Boolean>>,
    var question: MutableMap<String, MutableMap<String, QuestionStat>>,
    var pass_exams: Int,
    var all_exams: Int,
    var training_count: Int,
) {
    /**
     * Возвращает общее количество глав.
     */
    fun getTotalChaptersCount(): Int {
        return theory.size
    }

    /**
     * Возвращает количество прочитанных глав.
     * Глава считается прочитанной, если все её подглавы имеют значение true.
     */
    fun getReadChaptersCount(): Int {
        return theory.count { (_, subChapters) ->
            subChapters.all { it.value } // Проверяем, что все подглавы прочитаны
        }
    }
}

data class StatsJson(
    var attendance: MutableList<String>,
    @SerializedName("general_stats")
    var generalStats: MutableMap<String, GeneralStats>,
)