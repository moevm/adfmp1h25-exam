package com.example.examtrainer.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.examtrainer.R
import com.example.examtrainer.domain.model.ExamItem
import com.example.examtrainer.domain.model.StatisticData
import com.example.examtrainer.domain.model.Topic
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StatsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val SELECTED_EXAM_KEY = "selected_exam"
    }

    // Получаем выбранный экзамен из SharedPreferences
    fun getSelectedExam(): ExamItem? {
        val selectedExamName = sharedPreferences.getString(SELECTED_EXAM_KEY, null)
        return selectedExamName?.let { ExamItem(it.hashCode(), it) }
    }
    //TODO
    fun getAttendance() :Map<String, Boolean>{
        val inputStream =  context.resources.openRawResource(R.raw.stats)
    }

    fun getGeneralStats(): StatisticData {

    }

    fun getTopicsStats(): List<Topic>{
        //context.resources
    }


}