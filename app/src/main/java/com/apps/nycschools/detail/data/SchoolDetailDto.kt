package com.apps.nycschools.detail.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SchoolDetailDto(
    @Json(name = "school_name")
    val name: String,
    @Json(name = "phone_number")
    val phone: String,
    @Json(name = "school_email")
    val email: String,
    val website: String,
    @Json(name = "location")
    val address: String,
    val start_time: String,
    val end_time: String
)