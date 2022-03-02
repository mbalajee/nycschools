package com.apps.nycschools.list.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apps.nycschools.list.data.SchoolListDto

@Dao
interface SchoolListDao {
    @Query("SELECT * from SchoolList WHERE id >= ((:pageNo - 1) * :pageSize) LIMIT :pageSize")
    fun getByPage(pageNo: Int, pageSize: Int): List<SchoolListDto>

    @Insert
    fun insertAll(schools: List<SchoolListDto>)
}