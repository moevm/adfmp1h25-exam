package com.example.examtrainer.presentation.viewmodel.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examtrainer.data.local.ExamRepository
import com.example.examtrainer.data.local.TheoryRepository
import com.example.examtrainer.domain.model.ChapterQuestions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrainingTOCViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val theoryRepository: TheoryRepository
) : ViewModel() {
    private val _chapterQuestions = MutableStateFlow<List<ChapterQuestions>>(emptyList())
    val chapterQuestions: StateFlow<List<ChapterQuestions>> = _chapterQuestions

    private val _currentChapterIdx = MutableStateFlow(-1)
    val currentChapterIdx: StateFlow<Int> = _currentChapterIdx

    private val _currentExam = MutableStateFlow<String?>("")
    val currentExam: StateFlow<String?> = _currentExam

    init {
//        println("training toc")
        loadData()
    }

    fun selectChapter(chapterIdx: Int) {
        _currentChapterIdx.value = chapterIdx
//        println(_currentChapterIdx.value)
    }

    fun loadData() {
        _currentExam.value = examRepository.getSelectedExam()?.name
        println(currentExam)
        _chapterQuestions.value = theoryRepository.getChapters(
            examRepository.getSelectedOrDefaultExam().name,
        )
            .map { c ->
                ChapterQuestions(
                    title = c.title,
                    questions = c.questions,
                )
            }.filter { cq -> cq.questions.isNotEmpty() }
    }
}
