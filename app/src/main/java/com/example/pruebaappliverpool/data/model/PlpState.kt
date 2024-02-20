package com.example.pruebaappliverpool.data.model

data class PlpState(
    val area: String,
    val categoryId: String,
    val currentFilters: String,
    val currentSortOption: String,
    val firstRecNum: Int,
    val id: String,
    val lastRecNum: Int,
    val originalSearchTerm: String,
    val plpSellerName: String,
    val recsPerPage: Int,
    val totalNumRecs: Int
)