package com.example.examtrainer.presentation.state

sealed class TrainingScreenState {
    object Welcome : TrainingScreenState()
    object Question : TrainingScreenState()
    object Result : TrainingScreenState()
}