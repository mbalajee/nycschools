package com.apps.nycschools.detail.model

import com.apps.nycschools.detail.data.SchoolDetailRepository
import kotlinx.coroutines.*

class SchoolDetailPresenter(
    private val view: SchoolDetailMvp.View,
    private val schoolId: String,
    private val repo: SchoolDetailRepository
): SchoolDetailMvp.Presenter {

    override fun getSchoolDetail() {
        view.setLoading(true)
        IO {
            val detailD = async { repo.getSchoolDetail(schoolId) }
            val satResultD = async { repo.getSATResult(schoolId) }
            SchoolDetail(detailD.await(), satResultD.await()).also {
                UI {
                    view.showSchoolDetail(it)
                    view.setLoading(false)
                }
            }
        }
    }

    private fun IO(block: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                block()
            } catch (e: Throwable) {
                // Show appropriate message based on error code
                e.printStackTrace()
                view.showError(e.message ?: "")
            }
        }
    }

    private fun UI(block: suspend () -> Unit) {
        MainScope().launch {
            block()
        }
    }
}