package com.apps.nycschools.list.data.source

import com.apps.nycschools.list.data.SchoolListDto

class SchoolListRemoteDataSource(private val api: SchoolListApi): SchoolListDataSource {
    override suspend fun getSchools(pageNo: Int, pageSize: Int): List<SchoolListDto> =
        api.getSchools(pageNo, pageSize)
}