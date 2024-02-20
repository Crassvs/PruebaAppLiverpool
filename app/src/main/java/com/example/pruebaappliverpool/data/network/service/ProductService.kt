package com.example.pruebaappliverpool.data.network.service

import com.example.pruebaappliverpool.data.model.ProductItem
import com.example.pruebaappliverpool.data.network.api.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductService @Inject constructor(private val api: ProductApi){

    suspend fun getProduct(page_number:Int, search: String, sort:String, force: Boolean, numberitems: Int, clean: Boolean): ProductItem{
        return withContext(Dispatchers.IO){
            api.productApi(page_number = page_number, search= search, sort = sort, force= force, number_items= numberitems,clean= clean).body()!!
        }
    }
}