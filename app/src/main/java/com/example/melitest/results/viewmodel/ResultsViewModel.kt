package com.example.melitest.results.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melitest.api.MeliResponse
import com.example.melitest.api.RemoteMeliDataSource
import com.example.melitest.results.model.ResultLoading
import com.example.melitest.results.model.ResultNotFoundError
import com.example.melitest.results.model.ResultServerError
import com.example.melitest.results.model.ResultsState
import com.example.melitest.results.usecase.SearchProductUseCase
import java.net.HttpURLConnection
import kotlinx.coroutines.launch

class ResultsViewModel(private val useCase: SearchProductUseCase) : ViewModel() {
    private val _meliResponse = MutableLiveData<MeliResponse>()
    val meliResponse: LiveData<MeliResponse> = _meliResponse

    private val _resultState = MutableLiveData<ResultsState>()
    val resultsState: LiveData<ResultsState> = _resultState

    private var offset = 0
    private var mQuery = ""

    fun performSearch(query: String? = null) {
        _resultState.value = ResultLoading(true)
        if (!query.isNullOrEmpty()) {
            mQuery = query
        }
        viewModelScope.launch {
            val response = useCase.performSearch(mQuery, offset)
            if (HttpURLConnection.HTTP_OK == response.code()) {
                if (response.body()!!.meliItemResponseList.isEmpty()) {
                    _resultState.value = ResultNotFoundError
                } else {
                    _meliResponse.value = response.body()
                }
                offset++
            } else _resultState.value = ResultServerError
            _resultState.value = ResultLoading(false)
        }

    }


}

