package com.example.melitest.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val MELI_BASE_URL = "https://api.mercadolibre.com/sites/MLA/"

    val meliApi = Retrofit.Builder()
        .baseUrl(MELI_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MeliService::class.java)
}