package com.apps.nycschools

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apps.nycschools.list.data.SchoolListDto
import com.apps.nycschools.list.data.source.SchoolListDao

@Database(entities = [SchoolListDto::class], version = 1)
abstract class SchoolDatabase : RoomDatabase() {
    abstract fun schoolListDao(): SchoolListDao
}