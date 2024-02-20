package com.example.pruebaappliverpool.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.pruebaappliverpool.data.network.service.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService
){
    fun getProductRepository() = Pager(
        config = PagingConfig(pageSize = 40, prefetchDistance = 5),
        pagingSourceFactory = { NewsPagingSource(productService = productService) }
    ).flow
}