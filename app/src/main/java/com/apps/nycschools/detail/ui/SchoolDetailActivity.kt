package com.apps.nycschools.detail.ui

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.apps.nycschools.api.ApiProvider
import com.apps.nycschools.databinding.ActivitySchoolDetailBinding
import com.apps.nycschools.detail.data.SchoolDetailApi
import com.apps.nycschools.detail.data.SchoolDetailRepositoryImpl
import com.apps.nycschools.detail.model.SchoolDetail
import com.apps.nycschools.detail.model.SchoolDetailMvp
import com.apps.nycschools.detail.model.SchoolDetailPresenter

class SchoolDetailActivity : AppCompatActivity(), SchoolDetailMvp.View {

    companion object {
        const val KEY_INTENT_SCHOOL_ID = "school_id"
    }

    private lateinit var binding: ActivitySchoolDetailBinding

    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolDetailBinding.inflate(layoutInflater).apply {
            setContentView(root)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        intent.getStringExtra(KEY_INTENT_SCHOOL_ID)?.let {
            SchoolDetailPresenter(
                view = this,
                schoolId = it,
                repo = SchoolDetailRepositoryImpl(
                    ApiProvider.`for`(SchoolDetailApi::class.java)
                )
            ).getSchoolDetail()
        } ?: throw RuntimeException("School ID not provided")
    }

    override fun showSchoolDetail(schoolDetail: SchoolDetail) {
        supportActionBar?.title = schoolDetail.name
        binding.detail = schoolDetail
        binding.executePendingBindings()
    }

    override fun setLoading(enabled: Boolean) {
        if (loadingDialog == null) {
            loadingDialog = AlertDialog.Builder(this)
                .setView(
                    ProgressBar(this)
                )
                .create()
        }
        if (enabled) {
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }
        binding.schoolDetailContainer.isVisible = !enabled
    }

    override fun showError(message: String) {

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}