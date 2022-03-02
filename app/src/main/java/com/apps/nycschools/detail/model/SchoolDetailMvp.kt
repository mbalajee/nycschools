package com.apps.nycschools.detail.model

interface SchoolDetailMvp {
    interface View {
        fun showSchoolDetail(schoolDetail: SchoolDetail)
        fun setLoading(enabled: Boolean)
        fun showError(message: String)
    }
    interface Presenter {
        fun getSchoolDetail()
    }
}