package com.example.pruebaappliverpool.data.network.api

import com.example.pruebaappliverpool.data.model.ProductItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("appclienteservices/services/v7/plp/sf")
    suspend fun productApi(
        @Query("page-number") page_number: Int,
        @Query("search-string") search: String,
        @Query("sort-option") sort: String,
        @Query("force-plp") force: Boolean,
        @Query("number-of-items-per-page") number_items: Int,
        @Query("force-plp") clean: Boolean): Response<ProductItem>
}