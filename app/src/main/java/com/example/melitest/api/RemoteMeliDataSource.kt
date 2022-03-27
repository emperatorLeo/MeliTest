package com.example.melitest.api

import retrofit2.Response

class RemoteMeliDataSource {

    suspend fun searchProduct(query: String, offset: Int = 0): Response<MeliResponse> {
        return RetrofitClient.meliApi
            .getProductsList(query, offset = offset)
    }
}