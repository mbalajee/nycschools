package com.apps.nycschools.list.data

import android.util.Log
import com.apps.nycschools.list.data.source.SchoolListLocalDataSource
import com.apps.nycschools.list.data.source.SchoolListRemoteDataSource

class SchoolListRepositoryImpl(
    private val local: SchoolListLocalDataSource,
    private val remote: SchoolListRemoteDataSource
): SchoolListRepository {

    override suspend fun getSchools(pageNo: Int, pageSize: Int): List<SchoolListDto> {
        updateLocalIfEmpty(pageNo, pageSize)
        return local.getSchools(pageNo, pageSize).also {
            Log.d("SchoolPagination", "Repo returning from local $pageNo count ${it.size}")
        }
    }

    private suspend fun updateLocalIfEmpty(pageNo: Int, pageSize: Int) {
        local.getSchools(pageNo, pageSize).let {
            if (it.isEmpty()) {
                Log.d("SchoolPagination", "Repo Remote loading page $pageNo")
                local.add(
                    remote.getSchools(pageNo, pageSize).also {
                        Log.d("SchoolPagination", "Repo Remote loaded page $pageNo count ${it.size}")
                    }
                )
            }
        }
    }
}