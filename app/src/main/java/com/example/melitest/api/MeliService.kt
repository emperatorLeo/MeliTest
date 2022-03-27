package com.example.melitest.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MeliService {

    @GET("search")
    suspend fun getProductsList(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int
    ): Response<MeliResponse>
}