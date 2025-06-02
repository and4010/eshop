package com.example.eshop.api

import retrofit2.http.GET
import retrofit2.http.Url

interface ProductApiService {

    @GET
    suspend fun getProducts(@Url url: String): ProductResponse
}