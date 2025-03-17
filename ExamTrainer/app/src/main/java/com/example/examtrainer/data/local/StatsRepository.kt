package com.example.examtrainer.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.examtrainer.R
import com.example.examtrainer.data.local.model.StatsFields
import com.example.examtrainer.data.local.model.StatsJson
import com.example.examtrainer.domain.model.ExamItem
import com.example.examtrainer.domain.model.StatisticData
import com.example.examtrainer.domain.model.Topic
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class StatsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val SELECTED_EXAM_KEY = "selected_exam"
        private const val STATS_FILE_NAME = "stats.json"
    }

    private val statsFile by lazy { File(context.filesDir, STATS_FILE_NAME) }
    private val gson = Gson()

    init {
        initStatsFile()
    }

    private fun initStatsFile() {
        if (!statsFile.exists()) {
            context.resources.openRawResource(R.raw.stats).use { input ->
                statsFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    private fun loadStatsData(): StatsJson {
        return statsFile.inputStream().reader().use { reader ->
            gson.fromJson(reader, StatsJson::class.java).apply {
                // Преобразование в mutable коллекции
                attendance = attendance.toMutableMap()
                generalStats = generalStats.mapValuesTo(LinkedHashMap()) { it.value }
                topicsStats = topicsStats.mapValues { it.value.toMutableMap() }.toMutableMap()
            }
        }
    }

    private fun saveStatsData(data: StatsJson) {
        statsFile.writeText(gson.toJson(data))
    }

    // Получаем выбранный экзамен из SharedPreferences
    fun getSelectedExam(): ExamItem? {
        val selectedExamName = sharedPreferences.getString(SELECTED_EXAM_KEY, null)
        return selectedExamName?.let { ExamItem(it.hashCode(), it) }
    }

    // Возвращает мапу с true/false состоянием посещаемости для каждого дня недели
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

    //Записывает процент изученности темы для переданной темы и переданного экзамена
    fun setTopicStat(examName: String, topicName: String, percent: Int) {
//        System.out.println("topic " + examName + " " + topicName + " " + percent)
        val data = loadStatsData()
        val topics = data.topicsStats[examName]
            ?: throw IllegalArgumentException("Exam $examName not found")
        topics[topicName]?.let {
            topics[topicName] = percent
//            System.out.println("topic " + topics[topicName] + " " + data)
            saveStatsData(data)
        } ?: throw IllegalArgumentException("Topic $topicName not found")
    }

    //Устанавливает значение для переданного дня для отслеживания посещаемости
    fun setDayAttendance(day: String, value: Boolean) {
        val data = loadStatsData().apply {
            attendance[day] = value // Работает с MutableMap
        }
        saveStatsData(data)
    }

    //Устанавливает все значения посещаемости (для всех дней недели) в false
    fun resetAllDaysAttendance() {
        val data = loadStatsData().apply {
            attendance.keys.forEach { day ->
                attendance[day] = false
            }
        }
        saveStatsData(data)
    }

    //Записывает значение в поле общей статистики (можно взять в StatsFields enum) для переданной темы и переданного экзамена
    fun setGeneralStatField(examName: String, statField: String, value: Int) {
//        System.out.println("general " + examName + " " + statField + " " + value)
        val field = try {
            StatsFields.valueOf(statField)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid field: $statField")
        }

        val data = loadStatsData()
        val stats = data.generalStats[examName]
            ?: throw IllegalArgumentException("Exam $examName not found")

        when (field) {
            StatsFields.readChapters -> stats.read_chapt = value
            StatsFields.allChapters -> stats.all_chapt = value
            StatsFields.readTopics -> stats.read_topics = value
            StatsFields.allTopics -> stats.all_topics = value
            StatsFields.answerQuestions -> stats.answer_questions = value
            StatsFields.allQuestions -> stats.all_questions = value
            StatsFields.passExams -> stats.pass_exams = value
            StatsFields.allExams -> stats.all_exams = value
            StatsFields.trainingCount -> stats.training_count = value
        }
//        System.out.println("general "+ stats + " " + data)
        saveStatsData(data)
    }

    //Увеличивает на 1 значение в поле общей статистики (можно взять в StatsFields enum) для переданной темы и переданного экзамена
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
            StatsFields.readChapters -> stats.read_chapt++
            StatsFields.allChapters -> stats.all_chapt++
            StatsFields.readTopics -> stats.read_topics++
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