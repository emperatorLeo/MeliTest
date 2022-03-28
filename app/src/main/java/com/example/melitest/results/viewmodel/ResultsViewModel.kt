package com.example.melitest.results.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melitest.api.MeliResponse
import com.example.melitest.api.RemoteMeliDataSource
import java.net.HttpURLConnection
import kotlinx.coroutines.launch

class ResultsViewModel() : ViewModel() {
    private val _meliResponse = MutableLiveData<MeliResponse>()
    val meliResponse: LiveData<MeliResponse> = _meliResponse
    private var offset = 0
    private var mQuery = ""

    fun performSearch(query: String? = null, isScrolledDown: Boolean) {
        if (!query.isNullOrEmpty()) {
            mQuery = query
        }

        viewModelScope.launch {
            Log.d("Leo", "isScrolledDown: $isScrolledDown, mquery: $mQuery, offset: $offset")
            val response = RemoteMeliDataSource().searchProduct(mQuery, offset)
            if (HttpURLConnection.HTTP_OK == response.code()) {
                _meliResponse.value = response.body()
            }
            /*if (isScrolledDown) {
                val response = RemoteMeliDataSource().searchProduct(mQuery, offset)
                if (HttpURLConnection.HTTP_OK == response.code()) {
                    _meliResponse.value = response.body()
                }
            } else {
                val response = RemoteMeliDataSource().searchProduct(query!!)
                if (HttpURLConnection.HTTP_OK == response.code()) {
                    _meliResponse.value = response.body()
                }
            }*/
            offset++
        }
    }
}

