package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.examtrainer.data.local.TheoryRepository
import com.example.examtrainer.domain.model.Chapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TheoryViewModel : ViewModel() {
    private val _repo: TheoryRepository = TheoryRepository()

    private val _chapters = MutableStateFlow<List<Chapter>>(emptyList())
    val chapters: StateFlow<List<Chapter>> = _chapters

    private val _currentChapterIdx = MutableStateFlow(0)
    val currentChapterIdx: StateFlow<Int> = _currentChapterIdx

    private val _currentSectionIdx = MutableStateFlow(0)
    val currentSectionIdx: StateFlow<Int> = _currentSectionIdx

    init {
        loadData()
    }

    fun selectChapter(chapterIdx: Int) {
        _currentChapterIdx.value = chapterIdx
    }

    fun selectSection(sectionIdx: Int) {
        _currentSectionIdx.value = sectionIdx
    }

    private fun loadData() {
        _chapters.value = _repo.getChapters()
    }
}