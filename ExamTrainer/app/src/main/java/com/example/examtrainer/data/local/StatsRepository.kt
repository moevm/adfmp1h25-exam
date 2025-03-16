package com.example.examtrainer.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.examtrainer.R
import com.example.examtrainer.data.local.model.DayOfWeek
import com.example.examtrainer.data.local.model.StatsFields
import com.example.examtrainer.data.local.model.StatsJson
import com.example.examtrainer.domain.model.ExamItem
import com.example.examtrainer.domain.model.StatisticData
import com.example.examtrainer.domain.model.Topic
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
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
    private val statsFile by lazy { File(context.filesDir, "stats.json") }

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
    private fun saveStatsData(data: StatsJson) {
        statsFile.writeText(Gson().toJson(data))
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

    fun setTopicStat(examName: String, topicName: String, percent: Int) {
        val data = loadStatsData()
        val topics = data.topicsStats[examName]
            ?: throw IllegalArgumentException("Exam $examName not found")
        topics[topicName]?.let {
            topics[topicName] = percent.coerceIn(0..100)
            saveStatsData(data)
        } ?: throw IllegalArgumentException("Topic $topicName not found")
    }

    fun setDayAttendance(day: String, value: Boolean) {
        val data = loadStatsData().apply {
            attendance[day] = value // Работает с MutableMap
        }
        saveStatsData(data)
    }

    fun resetAllDaysAttendance() {
        val data = loadStatsData().apply {
            attendance.keys.forEach { day ->
                attendance[day] = false
            }
        }
        saveStatsData(data)
    }

    fun setGeneralStatField(examName: String, statField: String, value: Int) {
        val field = try {
            StatsFields.valueOf(statField)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid field: $statField")
        }

        val data = loadStatsData()
        val stats = data.generalStats[examName]
            ?: throw IllegalArgumentException("Exam $examName not found")

        when (field) {
            StatsFields.readChapters -> stats.readed_chapt = value
            StatsFields.allChapters -> stats.all_chapt = value
            StatsFields.readTopics -> stats.readed_topics = value
            StatsFields.allTopics -> stats.all_topics = value
            StatsFields.answerQuestions -> stats.answer_questions = value
            StatsFields.allQuestions -> stats.all_questions = value
            StatsFields.passExams -> stats.pass_exams = value
            StatsFields.allExams -> stats.all_exams = value
            StatsFields.trainingCount -> stats.training_count = value
        }
        saveStatsData(data)
    }

    fun incrementGeneralStatField(examName: String, statField: String) {
        val field = try {
            StatsFields.valueOf(statField)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid field: $statField")
        }

        val data = loadStatsData()
        val stats = data.generalStats[examName]
            ?: throw IllegalArgumentException("Exam $examName not found")

        when (field) {
            StatsFields.readChapters -> stats.readed_chapt++
            StatsFields.allChapters -> stats.all_chapt++
            StatsFields.readTopics -> stats.readed_topics++
            StatsFields.allTopics -> stats.all_topics++
            StatsFields.answerQuestions -> stats.answer_questions++
            StatsFields.allQuestions -> stats.all_questions++
            StatsFields.passExams -> stats.pass_exams++
            StatsFields.allExams -> stats.all_exams++
            StatsFields.trainingCount -> stats.training_count++
        }
        saveStatsData(data)
    }




}