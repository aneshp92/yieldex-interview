package com.yieldex.interview.data.model.response

data class PaginatedFilteredTransactionResponse(
    val transactions: List<FilteredTransactionResponse>,
    val pageSize: Int,
    val page: Int,
    val totalPages: Int,
    val totalItems: Int,
)
