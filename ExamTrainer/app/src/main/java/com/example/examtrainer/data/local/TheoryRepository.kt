package com.example.examtrainer.data.local

import android.content.Context
import com.example.examtrainer.R
import com.example.examtrainer.domain.model.Chapter
import com.example.examtrainer.domain.model.Question
import com.example.examtrainer.domain.model.Section
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TheoryRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun getChapters(examName: String): List<Chapter> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.theory)
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val examMapType = object :
                TypeToken<Map<String, Map<String, Map<String, List<String>>>>>() {}.type
            val examMap: Map<String, Map<String, Map<String, List<String>>>> =
                Gson().fromJson(jsonString, examMapType)

            val inputStreamQuestions = context.resources.openRawResource(R.raw.questions)
            val jsonStringQuestions = inputStreamQuestions.bufferedReader().use { it.readText() }

            val questionsMapType = object :
                TypeToken<Map<String, Map<String, List<Question>>>>() {}.type
            val questionsMap: Map<String, Map<String, List<Question>>> =
                Gson().fromJson(jsonStringQuestions, questionsMapType)

            val questions: List<Question> = questionsMap.flatMap { (_, chapters) ->
                chapters.flatMap { (_, questions) -> questions }
            }

            val chapters: Map<String, List<Chapter>> = examMap.mapValues { (_, chaptersMap) ->
                chaptersMap.map { (chapterTitle, sectionsMap) ->
                    Chapter(
                        title = chapterTitle,
                        sections = sectionsMap.map { (sectionTitle, contentList) ->
                            Section(
                                title = sectionTitle,
                                content = contentList.joinToString(separator = "\n"),
                            )
                        },
                        questions = questions
                    )
                }
            }

            return chapters.getOrDefault(examName, emptyList())
        } catch (e: Exception) {
            emptyList()
        }
    }
}