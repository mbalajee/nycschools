package com.apps.nycschools.list.data.source

import com.apps.nycschools.list.data.SchoolListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolListApi {
    @GET("/resource/s3k6-pzi2.json?\$select=school_name,dbn")
    suspend fun getSchools(
        @Query("\$offset") pageNo: Int,
        @Query("\$limit") pageSize: Int
    ): List<SchoolListDto>
}