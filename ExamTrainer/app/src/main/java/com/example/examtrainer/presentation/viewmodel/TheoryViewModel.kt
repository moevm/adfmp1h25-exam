package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.examtrainer.data.local.ExamRepository
import com.example.examtrainer.data.local.StatsRepository
import com.example.examtrainer.data.local.TheoryRepository
import com.example.examtrainer.domain.model.Chapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class TheoryViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val theoryRepository: TheoryRepository,
    private val statsRepository: StatsRepository
) : ViewModel() {
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
        val curExam = examRepository.getSelectedExam()
        val curChapter =  _chapters.value[_currentChapterIdx.value]
        val curSection = curChapter.sections[_currentSectionIdx.value]

        statsRepository.setReadedChapterSection(curExam!!.name, curChapter.title, curSection.title)
    }

    private fun loadData() {
        _chapters.value = theoryRepository.getChapters(
            examRepository.getSelectedOrDefaultExam().name,
        )
    }
}