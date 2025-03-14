package com.example.examtrainer.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.examtrainer.R
import com.example.examtrainer.domain.model.Exam
import com.example.examtrainer.domain.model.ExamsResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ExamRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val SELECTED_EXAM_KEY = "selected_exam"
    }

    // Получаем список всех экзаменов из ресурсов
    fun getAllExams(): List<Exam> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.exams)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val response = Gson().fromJson(jsonString, ExamsResponse::class.java)

            response.all_exams.map { name: String ->
                Exam(id = name.hashCode(), name = name)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Получаем выбранный экзамен из SharedPreferences
    fun getSelectedExam(): Exam? {
        val selectedExamName = sharedPreferences.getString(SELECTED_EXAM_KEY, null)
        return selectedExamName?.let { Exam(it.hashCode(), it) }
    }

    // Сохраняем выбранный экзамен в SharedPreferences
    fun saveSelectedExam(exam: Exam) {
        sharedPreferences.edit().putString(SELECTED_EXAM_KEY, exam.name).apply()
    }

    // Выбираем первый экзамен по лексикографическому порядку, если нет выбранного
    fun getDefaultExam(): Exam {
        val exams = getAllExams()
        return exams.minByOrNull { it.name } ?: throw IllegalStateException("No exams found")
    }
}