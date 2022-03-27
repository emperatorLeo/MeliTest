package com.example.melitest.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melitest.api.MeliResponse
import com.example.melitest.api.RemoteMeliDataSource
import java.net.HttpURLConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultsViewModel() : ViewModel() {
    private val _meliResponse = MutableLiveData<MeliResponse>()
    val meliResponse: LiveData<MeliResponse> = _meliResponse
    private val offset = 0

    fun performSearch(query: String) {
        viewModelScope.launch {
            val response = RemoteMeliDataSource().searchProduct(query)
            if (HttpURLConnection.HTTP_OK == response.code()) {
                _meliResponse.value = response.body()
            }
        }
    }

}