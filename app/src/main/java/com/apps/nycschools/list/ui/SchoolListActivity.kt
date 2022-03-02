package com.apps.nycschools.list.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.nycschools.SchoolApplication
import com.apps.nycschools.api.ApiProvider
import com.apps.nycschools.databinding.ActivitySchoolListBinding
import com.apps.nycschools.detail.ui.SchoolDetailActivity
import com.apps.nycschools.list.data.SchoolListRepositoryImpl
import com.apps.nycschools.list.data.source.SchoolListApi
import com.apps.nycschools.list.data.source.SchoolListLocalDataSource
import com.apps.nycschools.list.data.source.SchoolListRemoteDataSource
import com.apps.nycschools.list.model.School
import com.apps.nycschools.list.model.SchoolListMvp
import com.apps.nycschools.list.model.SchoolListMvp.Loading.*
import com.apps.nycschools.list.model.SchoolListPresenter

class SchoolListActivity : AppCompatActivity(), SchoolListMvp.View {

    private val schoolListAdapter: SchoolListAdapter by lazy { SchoolListAdapter(
        onClickSchool = { selectedSchoolId ->
            startActivity(
                Intent(this, SchoolDetailActivity::class.java).apply {
                    putExtra(SchoolDetailActivity.KEY_INTENT_SCHOOL_ID, selectedSchoolId)
                }
            )
        }
    ) }

    private val presenter: SchoolListMvp.Presenter by lazy {
        SchoolListPresenter(
            this,
            SchoolListRepositoryImpl(
                SchoolListLocalDataSource((application as SchoolApplication).schoolDatabase.schoolListDao()),
                SchoolListRemoteDataSource(ApiProvider.`for`(SchoolListApi::class.java))
            )
        )
    }

    private lateinit var binding: ActivitySchoolListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolListBinding.inflate(layoutInflater).apply {
            setContentView(root)
            schoolList.adapter = schoolListAdapter
        }
        addScrollListenerForLoadMore()
        presenter.getSchools()
    }

    private fun addScrollListenerForLoadMore() {
        binding.schoolList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    with(recyclerView.layoutManager as LinearLayoutManager) {
                        if (findLastCompletelyVisibleItemPosition() == schoolListAdapter.itemCount - 1
                            && !schoolListAdapter.isLoading) {
                            Log.d("SchoolPagination", "Loading more")
                            presenter.getMoreSchools()
                        }
                    }
                }
            }
        })
    }

    override fun setLoading(loading: SchoolListMvp.Loading) {
        when (loading) {
            Initial -> binding.loading.isVisible = true
            LoadMore -> schoolListAdapter.setLoading(true)
            None -> {
                binding.loading.isVisible = false
                schoolListAdapter.setLoading(false)
            }
        }
    }

    override fun showSchools(schools: List<School>) {
        schoolListAdapter.addSchools(schools)
    }

    override fun showError(message: String) {
        // Show an error dialog on may be, API failure and use analytics to log this error
    }
}