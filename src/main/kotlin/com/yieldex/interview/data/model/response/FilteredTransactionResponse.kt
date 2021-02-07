package com.yieldex.interview.data.model.response

data class FilteredTransactionResponse(
    val status: String,
    val userId: Long,
    val merchantId: Long,
    val amountInCents: Int,
    val timestamp: Long,
)
