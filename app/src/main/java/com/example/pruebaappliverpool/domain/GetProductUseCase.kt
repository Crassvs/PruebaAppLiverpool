package com.example.pruebaappliverpool.domain

import androidx.paging.PagingData
import com.example.pruebaappliverpool.data.model.Record
import com.example.pruebaappliverpool.data.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
){
     operator fun invoke(): Flow<PagingData<Record>> {
        return repository.getProductRepository()
    }
}