package com.apps.nycschools.detail.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SATResultDto(
    val dbn: String,
    @Json(name = "num_of_sat_test_takers")
    val testTakers: Int,
    @Json(name = "sat_critical_reading_avg_score")
    val avgReadingScore: Int,
    @Json(name = "sat_writing_avg_score")
    val avgWritingScore: Int,
    @Json(name = "sat_math_avg_score")
    val avgMathScore: Int
)