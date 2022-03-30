package com.example.melitest.results.usecase

import com.example.melitest.api.RemoteMeliDataSource

class SearchProductUseCase {

    suspend fun performSearch(query: String, offset: Int = 0) = RemoteMeliDataSource().searchProduct(query, offset)

}