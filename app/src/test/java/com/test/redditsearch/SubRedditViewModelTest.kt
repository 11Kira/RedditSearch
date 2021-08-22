package com.test.redditsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kira.TestCoroutineRule
import com.test.redditsearch.core.SubRedditDetailsEvent
import com.test.redditsearch.core.SubRedditEvent
import com.test.redditsearch.core.SubRedditSearchEvent
import com.test.redditsearch.core.UIEvent
import com.test.redditsearch.core.response.ApiData
import com.test.redditsearch.core.response.ApiResponse
import com.test.redditsearch.core.response.ApiSubredditResponse
import com.test.redditsearch.subreddit.SubRedditRepo
import com.test.redditsearch.subreddit.SubRedditViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SubRedditViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var subRedditRepo: SubRedditRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `#retrieveSubReddit should pass 'OnSuccessFetching'`() {
        val apiResponse = ApiResponse("", ApiData<List<ApiSubredditResponse>>("", "", listOf()))
        val eventsObserver = mockk<Observer<UIEvent>>().also {
            every {
                it.onChanged(any())
            } answers { }
        }
        testCoroutineRule.runBlockingTest {
            val viewModel = SubRedditViewModel()
            viewModel.events.observeForever(eventsObserver)

            coEvery {
                subRedditRepo.retrieveSubReddits()
            } returns apiResponse
            viewModel.retrieveSubreddits()
            verify {
                eventsObserver.onChanged(
                    SubRedditEvent.OnStartLoading(true)
                )
            }
            viewModel.events.removeObserver(eventsObserver)
        }
    }

    @Test
    fun `#searchSubReddit should pass 'OnSuccessFetching'`() {
        val apiResponse = ApiResponse("", ApiData<List<ApiSubredditResponse>>("", "", listOf()))
        val eventsObserver = mockk<Observer<UIEvent>>().also {
            every {
                it.onChanged(any())
            } answers { }
        }
        testCoroutineRule.runBlockingTest {
            val viewModel = SubRedditViewModel()
            viewModel.events.observeForever(eventsObserver)

            coEvery {
                subRedditRepo.searchSubReddit("gigabyte", "sr")
            } returns apiResponse
            viewModel.searchSubreddits("gigabyte", "sr")
            verify {
                eventsObserver.onChanged(
                    SubRedditSearchEvent.OnStartLoading(true)
                )
            }
            viewModel.events.removeObserver(eventsObserver)
        }
    }

    @Test
    fun `#retrieveSubRedditDetails should pass 'OnSuccessFetching'`() {
        val apiResponse = ApiResponse("", ApiData<List<ApiSubredditResponse>>("", "", listOf()))
        val eventsObserver = mockk<Observer<UIEvent>>().also {
            every {
                it.onChanged(any())
            } answers { }
        }
        testCoroutineRule.runBlockingTest {
            val viewModel = SubRedditViewModel()
            viewModel.events.observeForever(eventsObserver)

            coEvery {
                subRedditRepo.getSubRedditDetails("r/gigabyte")
            } returns apiResponse
            viewModel.getSubRedditDetails("r/gigabyte")
            verify {
                eventsObserver.onChanged(
                    SubRedditDetailsEvent.OnStartLoading(true)
                )
            }
            viewModel.events.removeObserver(eventsObserver)
        }
    }

    @Test
    fun `#retrieveSubReddit should send 'OnFailed' when an error is encountered during retrieval`() {
        val errorMessage = "Error"
        val eventsObserver = mockk<Observer<UIEvent>>().also {
            every {
                it.onChanged(any())
            } answers { }
        }
        testCoroutineRule.runBlockingTest {
            val viewModel = SubRedditViewModel()
            viewModel.events.observeForever(eventsObserver)
            viewModel.retrieveSubreddits()
            verify(exactly = 0) {
                eventsObserver.onChanged(SubRedditEvent.OnFailedFetching(errorMessage))
            }
            viewModel.events.removeObserver(eventsObserver)
        }
    }

    @Test
    fun `#searchSubReddit should send 'OnFailed' when an error is encountered during retrieval`() {
        val errorMessage = "Error"
        val eventsObserver = mockk<Observer<UIEvent>>().also {
            every {
                it.onChanged(any())
            } answers { }
        }
        testCoroutineRule.runBlockingTest {
            val viewModel = SubRedditViewModel()
            viewModel.events.observeForever(eventsObserver)
            viewModel.searchSubreddits("gigabyte", "sr")
            verify(exactly = 0) {
                eventsObserver.onChanged(SubRedditSearchEvent.OnFailedFetching(errorMessage))
            }
            viewModel.events.removeObserver(eventsObserver)
        }
    }

    @Test
    fun `#retrieveSubRedditDetails should send 'OnFailed' when an error is encountered during retrieval`() {
        val errorMessage = "Error"
        val eventsObserver = mockk<Observer<UIEvent>>().also {
            every {
                it.onChanged(any())
            } answers { }
        }
        testCoroutineRule.runBlockingTest {
            val viewModel = SubRedditViewModel()
            viewModel.events.observeForever(eventsObserver)
            viewModel.getSubRedditDetails("r/gigabyte")
            verify(exactly = 0) {
                eventsObserver.onChanged(SubRedditDetailsEvent.OnFailedFetching(errorMessage))
            }
            viewModel.events.removeObserver(eventsObserver)
        }
    }
}