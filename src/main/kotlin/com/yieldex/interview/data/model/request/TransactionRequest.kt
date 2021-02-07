package com.yieldex.interview.data.model.request

data class TransactionRequest(
    val userId: Long,
    val merchantId: Long,
    val amountInCents: Int,
    val timestamp: Long,
)
