package com.example.examtrainer.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.examtrainer.R
import com.example.examtrainer.domain.model.ExamItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    fun getAllExams(): List<ExamItem> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.exams)
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val examType = object : TypeToken<List<String>>() {}.type
            val examList: List<String> = Gson().fromJson(jsonString, examType)

            val res = examList.map { name: String ->
                ExamItem(id = name.hashCode(), name = name)
            }
            println(res)
            return res
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Получаем выбранный экзамен из SharedPreferences
    fun getSelectedExam(): ExamItem? {
        val selectedExamName = sharedPreferences.getString(SELECTED_EXAM_KEY, null)
        return selectedExamName?.let { ExamItem(it.hashCode(), it) }
    }

    // Сохраняем выбранный экзамен в SharedPreferences
    fun saveSelectedExam(exam: ExamItem) {
        sharedPreferences.edit().putString(SELECTED_EXAM_KEY, exam.name).apply()
    }

    // Выбираем первый экзамен по лексикографическому порядку, если нет выбранного
    fun getDefaultExam(): ExamItem {
        val exams = getAllExams()
        return exams.minByOrNull { it.name } ?: throw IllegalStateException("No exams found")
    }

    fun getSelectedOrDefaultExam(): ExamItem {
        return getSelectedExam() ?: getDefaultExam()
    }
}