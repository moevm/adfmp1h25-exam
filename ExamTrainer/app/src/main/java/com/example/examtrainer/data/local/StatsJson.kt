package com.example.examtrainer.data.local

import com.example.examtrainer.domain.model.StatisticData
import com.google.gson.annotations.SerializedName

data class StatsJson(
    val attendance: Map<String, Boolean>,
    @SerializedName("general_stats")
    val generalStats: Map<String, StatisticData>,
    @SerializedName("topics_stats")
    val topicsStats: Map<String, Map<String, Int>>
)