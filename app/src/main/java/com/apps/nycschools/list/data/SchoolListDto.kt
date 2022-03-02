package com.apps.nycschools.list.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*
 * Thoughts:
 * It's not a bad idea to have separate model for Room and API response
 * in which case I would have SchoolListEntity and SchoolListResponse
 *
 * This model is placed outside of [list/data/source] because this crosses
 * the border [list/data] package and passes through [list/model]
 */
/**
 * Represents the raw response returned by [com.apps.nycschools.list.data.source.SchoolListApi.getSchools]
 */
@Entity(tableName = "SchoolList")
@JsonClass(generateAdapter = true)
data class SchoolListDto(
    @PrimaryKey(autoGenerate = true)
    @Json(ignore = true)
    val id: Int = 0,

    @ColumnInfo(name = "school_id")
    val dbn: String,

    @ColumnInfo(name = "school_name")
    @Json(name = "school_name")
    val schoolName: String
)