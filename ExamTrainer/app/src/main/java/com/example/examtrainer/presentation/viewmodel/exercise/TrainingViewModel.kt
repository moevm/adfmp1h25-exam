package com.example.examtrainer.presentation.viewmodel.exercise

import com.example.examtrainer.data.local.ExamRepository
import com.example.examtrainer.data.local.StatsRepository
import com.example.examtrainer.data.local.TheoryRepository
import com.example.examtrainer.data.local.model.StatsFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val theoryRepository: TheoryRepository,
    private val statsRepository: StatsRepository
) : BaseTrainingViewModel() {

    init {
//        println("training")
        loadData()
    }

    override fun loadData() {
        val currentExam = examRepository.getSelectedExam()
//        println(theoryRepository.getChapters(currentExam!!.name))
        val questions = theoryRepository.getChapters(
            examRepository.getSelectedOrDefaultExam().name,
        )
            .map { c -> c.questions }
            .filter { q -> q.isNotEmpty() }
            .flatten()
        loadQuestions(questions)
    }

    override fun stopExercise() {
        super.stopExercise()
        statsRepository.incrementGeneralStatField(examRepository.getSelectedExam()!!.name, StatsFields.trainingCount.name)
//        println(statsRepository.getGeneralStats(examRepository.getSelectedExam()!!.name))
    }

    override fun confirmAnswer() {
        super.confirmAnswer()
        val curExam = examRepository.getSelectedExam()
        // Получаем текущий вопрос
        val currentQuestion = questions.value[currentIndex.value]
        // Получаем выбранный ответ
        val selectedAnswer = selectedAnswer.value
        // Проверяем, был ли ответ корректным
        val isCorrect = selectedAnswer == currentQuestion.correctAnswer
        statsRepository.setQuestionStat(
            exam = curExam!!.name,
            question = currentQuestion.text,
            correct = isCorrect
        )
    }
}