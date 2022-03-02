package com.apps.nycschools.detail.model

import com.apps.nycschools.detail.data.SATResultDto
import com.apps.nycschools.detail.data.SchoolDetailDto

class SchoolDetail(
    val name: String,
    val phone: String,
    val email: String,
    val website: String,
    val address: String,
    val hours: String,
    private val _satResult: SATResult?
) {
    constructor(detailDto: SchoolDetailDto, satResultDto: SATResultDto?): this(
        name = detailDto.name,
        phone = detailDto.address,
        email = detailDto.email,
        website = detailDto.website,
        address = detailDto.address,
        hours = "${detailDto.start_time} - ${detailDto.end_time}",
        _satResult = satResultDto?.let { SATResult(it) }
    )

    val satResult: String = _satResult?.let {
        """
            Total test takers: ${_satResult.totalTestTakers}
            Avg Reading score: ${_satResult.avgReadingScore}
            Avg Writing score: ${_satResult.avgWritingScore}
            Avg Math score: ${_satResult.avgMathScore}
        """.trimIndent()
    } ?: "SAT result not available" // TODO: Move to strings.xml to support Localization
}

class SATResult(
    val totalTestTakers: Int,
    val avgReadingScore: Int,
    val avgWritingScore: Int,
    val avgMathScore: Int
) {
    constructor(satResultDto: SATResultDto) : this(
        totalTestTakers = satResultDto.testTakers,
        avgReadingScore = satResultDto.avgReadingScore,
        avgWritingScore = satResultDto.avgWritingScore,
        avgMathScore = satResultDto.avgMathScore
    )
}