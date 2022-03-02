package com.apps.nycschools.list.model

import android.util.Log
import com.apps.nycschools.list.data.SchoolListRepository
import com.apps.nycschools.list.model.SchoolListMvp.Loading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SchoolListPresenter(
    private val view: SchoolListMvp.View,
    private val repository: SchoolListRepository
): SchoolListMvp.Presenter {

    private var currentPageIdx = 0

    override fun getSchools() {
        view.setLoading(Loading.Initial)
        IO {
            repository
                .getSchools(pageNo = currentPageIdx + 1)
                .map { School(it.dbn, it.schoolName) }
                .also {
                    UI {
                        view.showSchools(it)
                        view.setLoading(Loading.None)
                    }
                }
        }
    }

    override fun getMoreSchools() {
        view.setLoading(Loading.LoadMore)
        IO {
            repository
                .getSchools(pageNo = currentPageIdx + 1)
                .map { School(it.dbn, it.schoolName) }
                .also {
                    UI {
                        view.setLoading(Loading.None)
                        view.showSchools(it)
                        currentPageIdx += 1
                        Log.d("SchoolPagination", "Page $currentPageIdx loaded")
                    }
                }
        }
    }

    private fun IO(block: suspend () -> Unit) {
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