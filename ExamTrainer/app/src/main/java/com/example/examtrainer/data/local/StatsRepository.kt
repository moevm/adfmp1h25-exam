package com.example.examtrainer.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.examtrainer.R
import com.example.examtrainer.domain.model.ExamItem
import com.example.examtrainer.domain.model.StatisticData
import com.example.examtrainer.domain.model.Topic
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStreamReader
import javax.inject.Inject

class StatsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val SELECTED_EXAM_KEY = "selected_exam"
        private var STATS_FILE_NAME = R.raw.stats
    }

    // Загрузка данных из JSON
    private fun loadStatsData(): StatsJson {
        val inputStream = context.resources.openRawResource(STATS_FILE_NAME)
        return Gson().fromJson(InputStreamReader(inputStream), StatsJson::class.java)
    }

    // Получаем выбранный экзамен из SharedPreferences
    fun getSelectedExam(): ExamItem? {
        val selectedExamName = sharedPreferences.getString(SELECTED_EXAM_KEY, null)
        return selectedExamName?.let { ExamItem(it.hashCode(), it) }
    }
    // Посещаемость
    fun getAttendance(): Map<String, Boolean> {
        return loadStatsData().attendance
    }

    // Общая статистика
    fun getGeneralStats(): StatisticData {
        val selectedExam = getSelectedExam() ?: throw IllegalStateException("Exam not selected")
        val stats = loadStatsData().generalStats[selectedExam.name]
            ?: throw IllegalArgumentException("Stats not found for ${selectedExam.name}")
        return stats
    }

    // Статистика по темам
    fun getTopicsStats(): List<Topic> {
        val selectedExam = getSelectedExam() ?: throw IllegalStateException("Exam not selected")
        val topicsMap = loadStatsData().topicsStats[selectedExam.name]
            ?: throw IllegalArgumentException("Topics not found for ${selectedExam.name}")
        return topicsMap.map { (name, progress) -> Topic(name, progress) }
    }


}