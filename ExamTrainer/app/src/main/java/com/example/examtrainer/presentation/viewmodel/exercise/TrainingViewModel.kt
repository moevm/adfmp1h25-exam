package com.example.examtrainer.presentation.viewmodel.exercise

import com.example.examtrainer.data.local.ExamRepository
import com.example.examtrainer.data.local.TheoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val theoryRepository: TheoryRepository,
) : BaseTrainingViewModel() {

    init {
        println("training")
        loadData()
    }

    override fun loadData() {
        val currentExam = examRepository.getSelectedExam()
        println(currentExam)
        val questions = theoryRepository.getChapters(
            examRepository.getSelectedOrDefaultExam().name,
        )
            .map { c -> c.questions }
            .filter { q -> q.isNotEmpty() }
            .flatten()
        loadQuestions(questions)
    }
}