package com.example.examtrainer.data.local

import android.content.Context
import com.example.examtrainer.R
import com.example.examtrainer.domain.model.Chapter
import com.example.examtrainer.domain.model.Section
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TheoryRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getChapters(): List<Chapter> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.theory)
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val chapterMapType = object :
                TypeToken<Map<String, Map<String, List<String>>>>() {}.type
            val rawChapterMap: Map<String, Map<String, List<String>>> = Gson()
                .fromJson(jsonString, chapterMapType)

            rawChapterMap.map { (chapterTitle, sectionsMap) ->
                Chapter(
                    title = chapterTitle,
                    sections = sectionsMap.map { (sectionTitle, contentList) ->
                        Section(
                            title = sectionTitle,
                            content = contentList.joinToString(separator = "\n")
                        )
                    },
                    questions = emptyList(),
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}