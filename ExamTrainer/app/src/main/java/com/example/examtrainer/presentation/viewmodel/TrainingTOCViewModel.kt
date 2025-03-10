package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.examtrainer.data.local.TheoryRepository
import com.example.examtrainer.domain.model.ChapterQuestions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrainingTOCViewModel : ViewModel() {
    private val _repo: TheoryRepository = TheoryRepository()

    private val _chapterQuestions = MutableStateFlow<List<ChapterQuestions>>(emptyList())
    val chapterQuestions: StateFlow<List<ChapterQuestions>> = _chapterQuestions

    private val _currentChapterIdx = MutableStateFlow(-1)
    val currentChapterIdx: StateFlow<Int> = _currentChapterIdx

    init {
        loadData()
    }

    fun selectChapter(chapterIdx: Int) {
        _currentChapterIdx.value = chapterIdx
    }

    private fun loadData() {
        _chapterQuestions.value = _repo.getChapters()
            .map { c ->
                ChapterQuestions(
                    title = c.title,
                    questions = c.questions,
                )
            }.filter { cq -> cq.questions.isNotEmpty() }
    }
}
