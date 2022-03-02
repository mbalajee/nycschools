package com.apps.nycschools.list.data.source

import android.util.Log
import com.apps.nycschools.list.data.SchoolListDto

class SchoolListLocalDataSource(private val dao: SchoolListDao): SchoolListDataSource {

    override suspend fun getSchools(pageNo: Int, pageSize: Int): List<SchoolListDto>
        = if (pageNo <= 0) emptyList() else dao.getByPage(pageNo, pageSize).also {
              Log.d("SchoolPagination", "LocalDS loaded page no $pageNo count ${it.size}")
          }

    override suspend fun add(schools: List<SchoolListDto>)
        = dao.insertAll(schools)
}