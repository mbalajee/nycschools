package com.apps.nycschools.detail.data

import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolDetailApi {
    @GET("/resource/s3k6-pzi2.json")
    suspend fun getSchoolDetail(
        @Query("dbn") schoolId: String
    ): List<SchoolDetailDto>

    @GET("/resource/f9bf-2cp4.json")
    suspend fun getSchoolSATResult(
        @Query("dbn") schoolId: String
    ): List<SATResultDto>
}