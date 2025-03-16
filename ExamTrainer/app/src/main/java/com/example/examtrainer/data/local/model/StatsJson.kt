package com.example.examtrainer.data.local.model

import com.example.examtrainer.domain.model.StatisticData
import com.google.gson.annotations.SerializedName

data class StatsJson(
    var attendance: MutableMap<String, Boolean>,
    @SerializedName("general_stats")
    var generalStats: MutableMap<String, StatisticData>,
    @SerializedName("topics_stats")
    var topicsStats: MutableMap<String, MutableMap<String, Int>>
)