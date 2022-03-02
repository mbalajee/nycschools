package com.apps.nycschools.detail.data
/*
 * Thoughts:
 *
 * Haven't used LocalDataSource for SchoolDetail because
 * I felt that school details can change whereas
 * SchoolListApi (SchoolName and SchoolId) is unlikely to change.
 */
interface SchoolDetailRepository {
    suspend fun getSchoolDetail(schoolId: String): SchoolDetailDto
    suspend fun getSATResult(schoolId: String): SATResultDto?
}