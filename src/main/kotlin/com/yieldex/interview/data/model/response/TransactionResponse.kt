package com.yieldex.interview.data.model.response

data class TransactionResponse(
    val status: String,
    val userId: Long,
    val merchantId: Long,
    val userBalance: Int
)
