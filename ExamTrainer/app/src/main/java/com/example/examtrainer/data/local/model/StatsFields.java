package com.example.examtrainer.data.local.model;

public enum StatsFields {
//    readChapters("read_chapt"),
//    allChapters("all_chapt"),
//    readTopics("read_topics"),
//    allTopics("all_topics"),
//    answerQuestions("answer_questions"),
//    allQuestions("all_questions"),
    passExams("pass_exams"),
    allExams("all_exams"),
    trainingCount("training_count");

    private final String jsonName;

    StatsFields(String jsonName) {
        this.jsonName = jsonName;
    }

    public String getJsonName() {
        return jsonName;
    }
}
