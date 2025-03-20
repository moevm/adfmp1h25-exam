package com.example.examtrainer.presentation.viewmodel.exercise

import android.app.AsyncNotedAppOp
import com.example.examtrainer.data.local.ExamRepository
import com.example.examtrainer.data.local.StatsRepository
import com.example.examtrainer.data.local.TheoryRepository
import com.example.examtrainer.data.local.model.StatsFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Dictionary
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val theoryRepository: TheoryRepository,
    private val statsRepository: StatsRepository
) : BaseTrainingViewModel() {
    enum class AnswerState {
        NotSelected,
        Wrong,
        Correct,
    }

    private val _questionsHistory: MutableStateFlow<MutableMap<Int, MutableMap<String, AnswerState>>> =
        MutableStateFlow(mutableMapOf())
    val questionsHistory: StateFlow<MutableMap<Int, MutableMap<String, AnswerState>>> =
        _questionsHistory

    init {
        loadData()
    }

    override fun loadData() {
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
        statsRepository.incrementGeneralStatField(
            examRepository.getSelectedExam()!!.name,
            StatsFields.trainingCount.name,
        )
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

        _questionsHistory.value[currentIndex.value] = mutableMapOf()
        for (answer: String in currentQuestion.answers) {
            if (!selectedAnswer.equals(answer)) {
                if (answer == currentQuestion.correctAnswer) {
                    _questionsHistory.value[currentIndex.value]?.put(answer, AnswerState.Correct)
                } else {
                    _questionsHistory.value[currentIndex.value]?.put(answer, AnswerState.NotSelected)
                }
                continue
            }
            if (isCorrect) {
                _questionsHistory.value[currentIndex.value]?.put(answer, AnswerState.Correct)
            } else {
                _questionsHistory.value[currentIndex.value]?.put(answer, AnswerState.Wrong)
            }
        }

        statsRepository.setQuestionStat(
            exam = curExam!!.name,
            question = currentQuestion.text,
            correct = isCorrect
        )
    }
}