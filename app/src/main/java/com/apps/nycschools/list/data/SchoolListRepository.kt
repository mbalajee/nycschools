package com.apps.nycschools.list.data

interface SchoolListRepository {
    suspend fun getSchools(pageNo: Int, pageSize: Int = 50): List<SchoolListDto>
}