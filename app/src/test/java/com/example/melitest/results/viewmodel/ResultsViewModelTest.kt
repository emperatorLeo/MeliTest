package com.example.melitest.results.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.melitest.api.MeliResponse
import com.example.melitest.results.model.ResultNotFoundError
import com.example.melitest.results.model.ResultServerError
import com.example.melitest.results.model.ResultsState
import com.example.melitest.results.usecase.SearchProductUseCase
import com.example.melitest.results.viewmodel.DummyMeliResponse.getDummyMeliEmptyResponse
import com.example.melitest.results.viewmodel.DummyMeliResponse.getDummyMeliResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import retrofit2.Response

@ExperimentalCoroutinesApi
class ResultsViewModelTest {
    private lateinit var resultsViewModel: ResultsViewModel
    private lateinit var searchProductUseCase: SearchProductUseCase
    private lateinit var meliResponseObserver: Observer<MeliResponse>
    private lateinit var resultStateObserver: Observer<ResultsState>

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    @Before
    fun setUp() {
        searchProductUseCase = mock()
        resultsViewModel = ResultsViewModel(searchProductUseCase)
        meliResponseObserver = mock()
        resultsViewModel.meliResponse.observeForever(meliResponseObserver)
        resultStateObserver = mock()
        resultsViewModel.resultsState.observeForever(resultStateObserver)

    }

    @Test
    fun `when is a success response, observer should have a response`() =
        coroutineRule.runBlockingTest {
            // GIVEN
            Mockito.`when`(searchProductUseCase.performSearch(any(), any()))
                .thenAnswer { Response.success(getDummyMeliResponse()) }

            // WHEN
            resultsViewModel.performSearch("")

            // THEN
            verify(meliResponseObserver).onChanged(eq(getDummyMeliResponse()))

        }

    @Test
    fun `when is an empty response, meliResponse observer shouldn't be called and stateObserver should be ResultNotFoundError state`() =
        coroutineRule.runBlockingTest {
            // GIVEN
            Mockito.`when`(searchProductUseCase.performSearch(any(), any())).thenAnswer {
                Response.success(
                    getDummyMeliEmptyResponse()
                )
            }

            // WHEN
            resultsViewModel.performSearch("")

            // THEN
            verify(meliResponseObserver, never()).onChanged(null)
            verify(resultStateObserver).onChanged(ResultNotFoundError)

        }

    @Test
    fun `when is a fail response, meliResponse observer shouldn't be callet and stateObserver should be ResultServerError state`() =
        coroutineRule.runBlockingTest {
            // GIVEN
            Mockito.`when`(searchProductUseCase.performSearch(any(), any())).thenAnswer {
                Response.error<Throwable>(500, ResponseBody.create(null,"Error"))
            }

            // WHEN
            resultsViewModel.performSearch("")

            // THEN
            verify(meliResponseObserver, never()).onChanged(null)
            verify(resultStateObserver).onChanged(ResultServerError)
        }

}