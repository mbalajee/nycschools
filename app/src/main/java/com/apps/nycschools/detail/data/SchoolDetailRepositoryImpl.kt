package com.apps.nycschools.detail.data

class SchoolDetailRepositoryImpl(private val api: SchoolDetailApi): SchoolDetailRepository {
    // TODO: Handle invalid schoolId. App would crash on `first()` if school detail response is empty.
    override suspend fun getSchoolDetail(schoolId: String): SchoolDetailDto
        = api.getSchoolDetail(schoolId).first()

    override suspend fun getSATResult(schoolId: String): SATResultDto?
        = api.getSchoolSATResult(schoolId).firstOrNull()
}