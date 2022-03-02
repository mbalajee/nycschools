package com.apps.nycschools

import android.app.Application
import androidx.room.Room

class SchoolApplication: Application() {
    val schoolDatabase: SchoolDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            SchoolDatabase::class.java, "nyc-school"
        ).build()
    }
}