package com.example.examtrainer.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.examtrainer.R
import com.example.examtrainer.data.local.model.GeneralStats
import com.example.examtrainer.data.local.model.QuestionStat
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
    @ApplicationContext private val context: Context,
    private val examRepository: ExamRepository,
    private val theoryRepository: TheoryRepository
) {
    companion object {
        private const val STATS_FILE_NAME = "stats.json"
    }

    private val statsFile by lazy { File(context.filesDir, STATS_FILE_NAME) }
    private val gson = Gson()

    init {
        try {
            initStatsFile()

            if (!statsFile.exists()) {
                initStatsFile()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getInintExamState(examName: String): GeneralStats {
        val chapters = theoryRepository.getChapters(examName)

        // Создаем теорию
        val theoryStats = chapters.associate { chapter ->
            chapter.title to chapter.sections.associate { section ->
                section.title to false
            }.toMutableMap()
        }.toMutableMap()

        // Создаем статистику вопросов
        val questionStats = chapters.associate { chapter ->
            chapter.title to chapter.questions.associate { question ->
                question.text to QuestionStat(all = 0, right = 0)
            }.toMutableMap()
        }.toMutableMap()

        // Возвращаем объект GeneralStats
        return GeneralStats(
            theory = theoryStats,
            question = questionStats,
            pass_exams = 0,
            all_exams = 0,
            training_count = 0
        )
    }


    private fun initStatsFile() {
        try {
            val exams = examRepository.getAllExams()
            val examStats: MutableMap<String, GeneralStats> = exams.associate { exam ->
                exam.name to getInintExamState(exam.name)
            }.toMutableMap()
            val stats: StatsJson = StatsJson(
                attendance = mutableListOf("2025-03-15", "2025-03-14"),
                generalStats = examStats
            )
            println(stats)
            saveStatsData(stats)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadStatsData(): StatsJson {
        return try {
            statsFile.inputStream().reader().use { reader ->
                gson.fromJson(reader, StatsJson::class.java).apply {
                    // Преобразование в mutable коллекции
                    attendance = attendance.toMutableList()
                    generalStats = generalStats.mapValuesTo(LinkedHashMap()) { it.value }
//                    topicsStats = topicsStats.mapValues { it.value.toMutableMap() }.toMutableMap()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            initStatsFile()
            return loadStatsData() // Повторная загрузка после сброса
        }
    }

    private fun saveStatsData(data: StatsJson) {
        statsFile.writeText(gson.toJson(data))
    }

    // Общая статистика
    fun getGeneralStats(selectedExamName: String): GeneralStats {
//        val selectedExam = examRepository.getSelectedExam() ?: throw IllegalStateException("Exam not selected")
        val stats = loadStatsData().generalStats[selectedExamName]
            ?: throw IllegalArgumentException("Stats not found for ${selectedExamName}")
        return stats
    }

    fun setQuestionStat(exam: String, question: String, correct: Boolean) {
        // Загружаем данные
        val stats = loadStatsData()

        // Получаем GeneralStats для указанного предмета
        val generalStats = stats.generalStats[exam]
        if (generalStats != null) {
            var questionFound = false

            // Ищем вопрос во всех темах
            for ((theme, questions) in generalStats.question) {
                if (questions.containsKey(question)) {
                    // Обновляем статистику для найденного вопроса
                    val questionStat = questions[question]!!
                    if (correct) {
                        questionStat.right++
                    }
                    questionStat.all++
                    questionFound = true
                    break // Выходим из цикла, так как вопрос найден
                }
            }

            if (!questionFound) {
                println("Вопрос '$question' не найден в предмете '$exam'.")
            }
        } else {
            println("Предмет '$exam' не найден.")
        }

        // Сохраняем обновленные данные
        saveStatsData(stats)
    }

    fun setReadedChapterSection(exam: String, chapter: String, section: String) {
        // Загружаем данные
        val stats = loadStatsData()

        // Получаем GeneralStats для указанного предмета
        val generalStats = stats.generalStats[exam]
        if (generalStats != null) {
            // Получаем теорию для указанной главы
            val theory = generalStats.theory[chapter]
            if (theory != null) {
                // Устанавливаем значение true для указанной секции
                theory[section] = true
            } else {
                println("Глава $chapter не найдена в предмете $exam.")
            }
        } else {
            println("Предмет $exam не найден.")
        }

        // Сохраняем обновленные данные
        saveStatsData(stats)
    }

    // Возвращает мапу с true/false состоянием посещаемости для каждого дня недели
    fun getAttendance(): List<String> {
        return loadStatsData().attendance
    }

    // Устанавливает значение для переданного дня для отслеживания посещаемости
    fun setDayAttendance(day: String) {
        val stats = loadStatsData()
        val data = stats.apply {
            // Проверяем, есть ли уже такая дата в списке
            if (!attendance.contains(day)) {
                // Добавляем дату в конец списка
                attendance.add(day)
                // Если список превышает длину 7, удаляем первый элемент
                if (attendance.size > 7) {
                    attendance.removeAt(0)
                }
            }
        }
        saveStatsData(data)
    }

    //Устанавливает все значения посещаемости (для всех дней недели) в false
    fun resetAllDaysAttendance() {
        val data = loadStatsData().apply {
            // Очищаем список
            attendance.clear()
        }
        saveStatsData(data)
    }

    //Записывает значение в поле общей статистики (можно взять в StatsFields enum) для переданной темы и переданного экзамена
//    fun setGeneralStatField(examName: String, statField: String, value: Int) {
////        System.out.println("general " + examName + " " + statField + " " + value)
//        val field = try {
//            StatsFields.valueOf(statField)
//        } catch (e: IllegalArgumentException) {
//            throw IllegalArgumentException("Invalid field: $statField")
//        }
//
//        val data = loadStatsData()
//        val stats = data.generalStats[examName]
//            ?: throw IllegalArgumentException("Exam $examName not found")
//
//        when (field) {
//            StatsFields.readChapters -> stats.read_chapt = value
//            StatsFields.allChapters -> stats.all_chapt = value
//            StatsFields.readTopics -> stats.read_topics = value
//            StatsFields.allTopics -> stats.all_topics = value
//            StatsFields.answerQuestions -> stats.answer_questions = value
//            StatsFields.allQuestions -> stats.all_questions = value
//            StatsFields.passExams -> stats.pass_exams = value
//            StatsFields.allExams -> stats.all_exams = value
//            StatsFields.trainingCount -> stats.training_count = value
//        }
////        System.out.println("general "+ stats + " " + data)
//        saveStatsData(data)
//    }
//
    //Увеличивает на 1 значение в поле общей статистики (можно взять в StatsFields enum) для переданной темы и переданного экзамена
    fun incrementGeneralStatField(examName: String, statField: String) {
        val field = try {
            StatsFields.valueOf(statField)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid field: $statField")
        }

        val data = loadStatsData()
        println(data)
        val stats = data.generalStats[examName]
            ?: throw IllegalArgumentException("Exam $examName not found")

        when (field) {
//            StatsFields.readChapters -> stats.read_chapt++
//            StatsFields.allChapters -> stats.all_chapt++
//            StatsFields.readTopics -> stats.read_topics++
//            StatsFields.allTopics -> stats.all_topics++
//            StatsFields.answerQuestions -> stats.answer_questions++
//            StatsFields.allQuestions -> stats.all_questions++
            StatsFields.passExams -> stats.pass_exams++
            StatsFields.allExams -> stats.all_exams++
            StatsFields.trainingCount -> stats.training_count++
        }
        saveStatsData(data)
    }




}