package com.apps.nycschools.list.data.source

import androidx.annotation.Size
import com.apps.nycschools.list.data.SchoolListDto

interface SchoolListDataSource {
    /**
     * Fetches the Schools list from either [SchoolListLocalDataSource] or
     * [SchoolListRemoteDataSource] based on if the requested [pageNo]
     * exists on local data source or not
     *
     * @param pageNo The page number to request from data source starting from 1.
     * @param pageSize Number of schools to fetch per page
     */
    suspend fun getSchools(@Size(min = 1) pageNo: Int, pageSize: Int = 50): List<SchoolListDto>

    suspend fun add(schools: List<SchoolListDto>) {}
}