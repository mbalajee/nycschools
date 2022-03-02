package com.apps.nycschools.list.model

interface SchoolListMvp {

    enum class Loading {
        None,
        Initial,
        LoadMore
    }

    interface View {
        fun setLoading(loading: Loading)
        fun showSchools(schools: List<School>)
        fun showError(message: String)
    }

    interface Presenter {
        fun getSchools(): Unit
        fun getMoreSchools(): Unit
    }
}