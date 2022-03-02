package com.apps.nycschools

import com.apps.nycschools.detail.data.SATResultDto
import com.apps.nycschools.detail.data.SchoolDetailDto
import com.apps.nycschools.detail.data.SchoolDetailRepository
import com.apps.nycschools.detail.model.SchoolDetail
import com.apps.nycschools.detail.model.SchoolDetailMvp
import com.apps.nycschools.detail.model.SchoolDetailPresenter
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalCoroutinesApi
class SchoolDetailPresenterTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val mockView: SchoolDetailMvp.View = mockk(relaxed = true)

    private val presenter = SchoolDetailPresenter(
        schoolId = "123",
        view = mockView,
        repo = object : SchoolDetailRepository {
            override suspend fun getSchoolDetail(schoolId: String) = SchoolDetailDto(
                name = "",
                phone = "",
                email = "",
                website = "",
                address = "",
                start_time = "",
                end_time = ""
            )

            override suspend fun getSATResult(schoolId: String): SATResultDto? = null
        }
    )

    @Test
    fun testSatResultUnavailable() = mainCoroutineRule.runBlockingTest {

        presenter.getSchoolDetail()

        val slot = slot<SchoolDetail>()

        verify { mockView.showSchoolDetail(schoolDetail = capture(slot)) }

        assert(slot.captured.satResult == "SAT result not available") {
            "Unexpected message for unavailable SAT Result"
        }
    }

    @ExperimentalCoroutinesApi
    private fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) =
        this.testDispatcher.runBlockingTest {
            block()
        }

    @ExperimentalCoroutinesApi
    class MainCoroutineRule(
        val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    ) : TestWatcher() {

        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            Dispatchers.resetMain()
            testDispatcher.cleanupTestCoroutines()
        }
    }
}
